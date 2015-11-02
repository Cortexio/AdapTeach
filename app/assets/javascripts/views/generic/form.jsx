import React from 'react'
import json from '../../helpers/json'

import FCST from '../../constants/formConstants'

function signinInitialState() {
  return (
    {
      errors: [],
      formData: 
        this.props.fields.map((field, index) => {
          let obj = {}
          obj[field[FCST.FIELD.NAME]] = 
            {name: field[FCST.FIELD.NAME], element: field[FCST.FIELD.ELEMENT], mandatory: field[FCST.FIELD.MANDATORY], half: field[FCST.FIELD.HALF_SIZE],
              style: field[FCST.FIELD.STYLE], type: field[FCST.FIELD.TYPE], placeholder: field[FCST.FIELD.PLACEHOLDER], unique: field[FCST.FIELD.UNIQUE],
              options: field[FCST.FIELD.OPTIONS], multiple: field[FCST.FIELD.MULTIPLE], originalOptions: field[FCST.FIELD.OPTIONS], value: null,
              showOptions: false}
          return obj
        }).reduce((acc, field) => {
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
    let fields = this.props.fields.map((field, index) => {
      switch(field.element) {
        case FCST.FIELDS.INPUT : return this.renderInput(this.state.formData[field[FCST.FIELD.NAME]])
        case FCST.FIELDS.SELECT : return this.renderSelect(this.state.formData[field[FCST.FIELD.NAME]])
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

  renderSelect(field) {
    var options = field.options.map(function(option) {
      return (<li key={option.id} onClick={this._handleClickSelectMultiple.bind(null, option,field.name)}> <a href="#"> {option.text} </a> </li>)
    }.bind(this))
    let selectValue = this.state.formData[field.name].value
    var chosen = !selectValue ? '' : selectValue.map(function(item) {
      return (item.text + ' ')
    })
    return (
      <div>
        <input type="text" placeholder="Search" onChange={this._filterList.bind(null, field.name)} onFocus={this._handleFocus.bind(null, field.name)}/>
        { this.state.formData[field.name].showOptions ? <ul> <li key="cancel" onClick={this._handleCancel.bind(null, field.name)}> <a href="#"> Cancel </a> </li> {options} </ul> : null }
        {chosen}
      </div>
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
            if(!elem.value.match(mailRegex)) this.state.errors.push({field: elem.name, error: "Enter a valid email"})
            break;

          default: break;
        }
      } else if(elem.mandatory) this.state.errors.push({field: elem.name, error: "the field " + elem.placeholder.toLowerCase() + " is mandatory"})
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

  _handleChange(fieldname, event) {
    let elem = event.target
    this.state.formData[fieldname].value = elem.value || undefined
  },

  _handleClickSelectMultiple(value,fieldname, event) {

    if (!this.state.formData[fieldname].value) {
      this.state.formData[fieldname].value = new Array()
    }
    this.state.formData[fieldname].value.push({id: value.id, text: value.text})
    this.state.formData[fieldname].originalOptions = this.state.formData[fieldname].originalOptions.filter(function (option) {
      return option.id != value.id;
    })
    this.state.formData[fieldname].options = this.state.formData[fieldname].originalOptions
    this.state.formData[fieldname].showOptions = false
    this.setState(this.state)
  },

  _filterList(fieldname, event){
    var updatedList = this.state.formData[fieldname].originalOptions;
    updatedList = updatedList.filter(function(option){
      return option.text.toLowerCase().search(event.target.value.toLowerCase()) !== -1;
    });
    this.state.formData[fieldname].options = updatedList
    this.setState(this.state)
  },

  _handleFocus(fieldname, event) {
    this.state.formData[fieldname].showOptions = true
    this.setState(this.state)
  },

  _handleCancel(fieldname, event) {
    this.state.formData[fieldname].showOptions = false
    this.setState(this.state)
  }
})

export default form