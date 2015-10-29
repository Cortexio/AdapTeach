import React from 'react'
import json from '../../helpers/json'

import FCST from '../../constants/formConstants'

/**
* method : ajax call method
* fields : field list to put in the form
* submitAction: function to call when submitting the form
* submitValue: Submit button value
* onChange function in each field: success, error, reset (to call eventually if you wanna revert effects of success or error)
*
* return json object with method and params exemple : { method : "POST", params : { tata: "tata", toto: "toto" } }
*/

const SERVER_ERROR = "server"
const CLIENT_ERROR = "client"

function initErrors(fields) {
  return this.props.fields.map((field, index) => {
    let obj = {}
    obj[field[FCST.FIELD.NAME]] = []
    return obj
  }).reduce((acc, field) => {
    return json.extend(acc, field)
  })
}

function formInitialState() {
  return (
    {
      errors: initErrors.bind(this)(),
      formData: 
        this.props.fields.map((field, index) => {
          let obj = {}
          obj[field[FCST.FIELD.NAME]] = 
            {
              name: field[FCST.FIELD.NAME], 
              element: field[FCST.FIELD.ELEMENT], 
              mandatory: field[FCST.FIELD.MANDATORY],
              autocomplete: field[FCST.FIELD.AUTOCOMPLETE] !== undefined ? field[FCST.FIELD.AUTOCOMPLETE] ? "on" : "off" : "on",
              half: field[FCST.FIELD.HALF_SIZE],
              style: field[FCST.FIELD.STYLE] || '',
              type: field[FCST.FIELD.TYPE],
              placeholder: field[FCST.FIELD.PLACEHOLDER],
              onchange: field[FCST.FIELD.ONCHANGE],
              onblur: field[FCST.FIELD.ONBLUR],
              value: null
            }
          return obj
        }).reduce((acc, field) => {
          return json.extend(acc, field)
        })
    }
  )
}

export default React.createClass({
  propTypes: {
    method: React.PropTypes.string.isRequired,
    fields: React.PropTypes.array.isRequired,
    submitAction: React.PropTypes.func.isRequired,
    submitValue: React.PropTypes.string.isRequired
  },

  getInitialState: formInitialState,

  render() {
    let fields = this.props.fields.map((field, index) => {
      switch(field.element) {
        case FCST.FIELDS.INPUT : return this.renderInput(this.state.formData[field[FCST.FIELD.NAME]])
        default : console.log(field)
      }
    })

    return (
      <form method={this.props.method} onSubmit={this._handleSubmit}>
        {fields}
        <input type="submit" value={this.props.submitValue} />
      </form>
    )
  },

  renderInput(field) {
    let fieldErrors = this.state.errors[field.name]

    return (
      <div key={field.name} className={'input-wrapper' + (field.half ? ' half ' + field.half : '')} >
        <input type={field.type} autoComplete={field.autocomplete} 
          className={field.style || ''}
          placeholder={field.placeholder} name={field.name} 
          onBlur={this._handleBlur.bind(null, field)}
          onChange={this._handleChange.bind(null, field)} />
        <p id={field.name} className="feedback">{fieldErrors.length > 0 ? fieldErrors[0].value : ''}</p>
      </div>
    )
  },

  _resetClientErrors(fieldName) {
    this.state.errors[fieldName] = this.state.errors[fieldName].filter(e => e.type !== CLIENT_ERROR)
    this.setState(this.state)
  },

  _resetServerErrors(fieldName) {
    this.state.errors[fieldName] = this.state.errors[fieldName].filter(e => e.type !== SERVER_ERROR)
    this.setState(this.state)
  },

  _validateForm(fields) {
    for(let fieldname in fields) {
      this._resetClientErrors(fieldname)
      let elem = this.state.formData[fieldname]
      if(elem && elem.value != null) {
        switch(elem.type) {
          case FCST.FIELD_TYPES.PASSWORD: 
            if(elem.value == null || elem.value.length < 6) this.state.errors["password"].push({type: CLIENT_ERROR, value: "Enter a longer password"})
            break;

          case FCST.FIELD_TYPES.EMAIL: 
            var mailRegex = new RegExp('^.+\\@.+\\..+$')
            if(!elem.value.match(mailRegex)) this.state.errors["email"].push({type: CLIENT_ERROR, value: "Enter a valid email"})
            break;

          default: break;
        }
      } else if(elem.mandatory) this.state.errors[fieldname].push({type: CLIENT_ERROR, value: "Enter your " + elem.placeholder.toLowerCase()})
    }
    this.setState(this.state)
  },

  _handleBlur(field, event) {
    //internal
    let elem = event.target
    this._validateForm(json.tupled({}, field.name, this.state.formData[field.name]))
    //external
    if(field.onblur) this._handleActions(field, elem.value, this, elem, FCST.FIELD.ONBLUR)
  },

  _handleChange(field, event) {
    //internal
    let elem = event.target
    this.state.formData[field.name].value = elem.value || undefined
    //external
    if(field.onchange) this._handleActions(field, elem.value, this, elem, FCST.FIELD.ONCHANGE)
  },

  _handleActions(field, value, ctx, domNode, actionType) {
    field[actionType].action(value)

    .done((data, textStatus, jqXHR) => {
      ctx._resetServerErrors(field.name)

      if(field[actionType].reset) field[actionType].reset(domNode)
      if(field[actionType].success) field[actionType].success(domNode)
    })

    .fail((jqXHR, textStatus, errorThrown) => {
      ctx._resetServerErrors(field.name)
      if(field[actionType].reset) field[actionType].reset(domNode)
      if(field[actionType].error) field[actionType].error(domNode)
      let serverErrors = jqXHR.responseJSON.errors
      //set server errors on the state
      ctx._resetClientErrors(field.name) // to allow errors from server to be render instead of low priority client errors
      for(let key in serverErrors) ctx.state.errors[key].push({type: SERVER_ERROR, value: serverErrors[key]})
      ctx.setState(ctx.state)
    })
  },

  _isErrorsStateEmpty() {
    return json.values(this.state.errors).reduce((acc, current) => {
      return current.length > 0 ? false : acc
    }, true)
  },

  _handleSubmit(event) {
    event.preventDefault()
    this._validateForm(this.state.formData)
    
    if(this._isErrorsStateEmpty()) {
      let ctx = this

      let res = {}
      for(let fieldname in this.state.formData) {
        let elem = this.state.formData[fieldname]
        if(elem.value) json.tupled(res, elem.name, elem.value)
      }
      this.props.submitAction({params: res, method: this.props.method})
        .fail((jqXHR, textStatus, errorThrown) => {
          let serverErrors = jqXHR.responseJSON.errors
          for(let key in serverErrors) ctx.state.errors[key].push({type: SERVER_ERROR, value: serverErrors[key]})
          ctx.setState(ctx.state)
        })
    }
  }
})

