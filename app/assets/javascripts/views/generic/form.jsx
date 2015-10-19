import React from 'react'
import json from '../../helpers/json'

import FCST from '../../constants/formConstants'

function signinInitialState() {
  return (
    {
      errors: [],
      formData: 
        this.props.fields.map(function(field, index) {
          let obj = {}
          obj[field[FCST.FIELD.NAME]] = 
            {name: field[FCST.FIELD.NAME], element: field[FCST.FIELD.ELEMENT], mandatory: field[FCST.FIELD.MANDATORY], half: field[FCST.FIELD.HALF_SIZE],
              style: field[FCST.FIELD.STYLE], type: field[FCST.FIELD.TYPE], placeholder: field[FCST.FIELD.PLACEHOLDER], value: null}
          return obj
        }).reduce(function(acc, field) {
          return json.extend(acc, field)
        })
    }
  )
}

var form = React.createClass({
  propTypes: {
    method: React.PropTypes.string.isRequired,
    fields: React.PropTypes.array.isRequired,
    submitAction: React.PropTypes.func.isRequired,
    submitValue: React.PropTypes.string.isRequired
  },

  getInitialState: signinInitialState,

  render() {
    let self = this
    let fields = this.props.fields.map(function(field, index) {
      switch(field.element) {
        case FCST.FIELDS.INPUT : return self.renderInput(self.state.formData[field[FCST.FIELD.NAME]])
        default : console.log(field)
      }
    })

    return (
      <form method={this.props.method} onSubmit={this._validateForm}>
        {fields}
        <span className="feedback">{this.state.errors.length > 0 ? this.state.errors[0].error : ''}</span>
        <input type="submit" value={this.props.submitValue} />
      </form>
    )
  },

  renderInput(field) {
    return (
      <input key={field.name} type={field.type} 
        className={field.half ? 'half ' + field.half : '' + field.style || ''}
        placeholder={field.placeholder} name={field.name} 
        onChange={this._handleChange.bind(null, field.name)} />
    )
  },

  _resetErrors() {
    this.state.errors = []
    this.setState(this.state)
  },

  _validateForm(event) {
    event.preventDefault()
    this._resetErrors()
    var self = this
    for(let fieldname in this.state.formData) {
      let elem = this.state.formData[fieldname]
      if(elem && elem.value != null) {
        switch(elem.type) {
          case FCST.FIELD_TYPES.PASSWORD: 
            if(elem.value == null || elem.value.length < 6) self.state.errors.push({field: elem.name, error: "Enter a longer password"})
            break;

          case FCST.FIELD_TYPES.EMAIL: 
            var mailRegex = new RegExp('^.+\\@.+\\..+$')
            if(!elem.value.match(mailRegex)) self.state.errors.push({field: elem.name, error: "Enter a valid email"})
            break;

          default: break;
        }
      } else if(elem.mandatory) self.state.errors.push({field: elem.name, error: "the field " + elem.placeholder.toLowerCase() + " is mandatory"})
    }

    if(self.state.errors.length === 0) {
      let res = {}
      for(let fieldname in this.state.formData) {
      let elem = this.state.formData[fieldname]
        if(elem.value) json.tupled(res, elem.name, elem.value)
      }
      this.props.submitAction({params: res, method: self.props.method})
    }
    else this.setState(this.state)
  },

  _handleChange(fieldname, event) {
    this.state.formData[fieldname].value = event.target.value || undefined
  }
})

export default form

