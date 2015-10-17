import React from 'react'
import jsonMerge from 'merge'

import FORM_CONSTANTS from '../../constants/formConstants'

function signinInitialState() {
  return (
    {
      errors: [],
      formData: 
        this.props.fields.map(function(field, index) {
          let obj = {}
          obj[field[FORM_CONSTANTS.FIELD.NAME]] = 
            {name: field[FORM_CONSTANTS.FIELD.NAME], mandatory: field[FORM_CONSTANTS.FIELD.MANDATORY], type: field[FORM_CONSTANTS.FIELD.TYPE], value: null}
          return obj
        }).reduce(function(acc, field) {
          return jsonMerge(acc, field)
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
      switch(field[FORM_CONSTANTS.FIELD.ELEMENT]) {
        case FORM_CONSTANTS.FIELDS.INPUT : return self.renderInput(field)
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
      <input key={field[FORM_CONSTANTS.FIELD.NAME]} type={field[FORM_CONSTANTS.FIELD.TYPE]} 
        placeholder={field[FORM_CONSTANTS.FIELD.PLACEHOLDER]} name={field[FORM_CONSTANTS.FIELD.NAME]} 
        onChange={this._handleChange.bind(null, field[FORM_CONSTANTS.FIELD.NAME])} />
    )
  },

  hashPasswd(password) {
    return password
  },

  _resetErrors() {
    this.state.errors = []
  },

  _validateForm(event) {
    event.preventDefault();
    this._resetErrors();
    var self = this
    this.state.formData.forEach(function(elem, index) {
      if(elem && elem.value != null) {
        switch(elem.type) {
          case FORM_CONSTANTS.FIELD_TYPES.PASSWORD: 
            if(elem.value == null || elem.value.length < 6) self.state.errors.push({field: elem.name, error: "Enter a longer password"})
            break;

          case FORM_CONSTANTS.FIELD_TYPES.EMAIL: 
            var mailRegex = new RegExp('^.+\\@.+\\..+$')
            if(!elem.value.match(mailRegex)) self.state.errors.push({field: elem.name, error: "Enter a valid email"})
            break;

          default: break;
        }
      } else if(elem.mandatory) self.state.errors.push({field: elem.name, error: "the field " + elem.name + " is mandatory"})
    })
    if(self.state.errors.length === 0) {
      let res = {}
      this.state.formData.forEach(function(elem, index) {
        res[elem.name] = elem.value
      })
      this.props.submitAction(res)
    }
    else this.setState(this.state)
  },

  _handleChange(fieldname, event) {
    this.state.formData[fieldname].value = event.target.value || null
  }
})

export default form

