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

function formInitialState() {
  return (
    {
      errors: [],
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
              onchange: field[FCST.FIELD.ONCHANGE], value: null
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
      <form method={this.props.method} onSubmit={this._validateForm}>
        {fields}
        <span className="feedback">{this.state.errors.length > 0 ? json.values(this.state.errors[0])[0] : ''}</span>
        <input type="submit" value={this.props.submitValue} readOnly={this.state.errors.length > 0} />
      </form>
    )
  },

  renderInput(field) {
    return (
      <input key={field.name} type={field.type} autoComplete={field.autocomplete} 
        className={field.half ? 'half ' + field.half : '' + field.style || ''}
        placeholder={field.placeholder} name={field.name} 
        onBlur={this._handleBlur.bind(null, field)} />
    )
  },

  _resetErrors() {
    this.state.errors = []
    this.setState(this.state)
  },

  _validateForm(event) {
    event.preventDefault()
    this._resetErrors()
    for(let fieldname in this.state.formData) {
      let elem = this.state.formData[fieldname]
      if(elem && elem.value != null) {
        switch(elem.type) {
          case FCST.FIELD_TYPES.PASSWORD: 
            if(elem.value == null || elem.value.length < 6) this.state.errors.push({field: elem.name, error: "Enter a longer password"})
            break;

          case FCST.FIELD_TYPES.EMAIL: 
            var mailRegex = new RegExp('^.+\\@.+\\..+$')
            if(!elem.value.match(mailRegex)) this.state.errors.push({error: "Enter a valid email"})
            break;

          default: break;
        }
      } else if(elem.mandatory) this.state.errors.push({error: "the field " + elem.placeholder.toLowerCase() + " is mandatory"})
    }

    if(this.state.errors.length === 0) {
      let res = {}
      for(let fieldname in this.state.formData) {
      let elem = this.state.formData[fieldname]
        if(elem.value) json.tupled(res, elem.name, elem.value)
      }
      this.props.submitAction({params: res, method: this.props.method})
    }
    else this.setState(this.state)
  },

  _handleBlur(field, event) {
    let elem = event.target

    this.state.formData[field.name].value = elem.value || undefined
    if(field.onchange) {
      let self = this
      field.onchange.action(event.target.value)
      .done((data, textStatus, jqXHR) => {
        self._resetErrors()
        if(field.onchange.reset) field.onchange.reset(elem)
        if(field.onchange.success) field.onchange.success(elem)
      })
      .fail((jqXHR, textStatus, errorThrown) => {
        self._resetErrors()
        if(field.onchange.reset) field.onchange.reset(elem)
        if(field.onchange.error) field.onchange.error(elem)
        self.state.errors = JSON.parse(jqXHR.responseText).errors
        self.setState(self.state)
      })
    } else {
      this._resetErrors()
    }
  }
})

