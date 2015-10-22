import React from 'react'
import json from '../../helpers/json'

import Form from '../generic/form'
import FCST from '../../constants/formConstants'

var objectiveForm = React.createClass({
  getInitialState: function() {

    var itemsFetched = [
      {id: 1, text: "Make a sentence"},
      {id: 2, text: "Declare a variable"},
      {id: 3, text: "Make an addition"}
    ];

    var subObjectivesFetched = [
        {id: 1, text: "Master rational numbers"},
        {id: 2, text: "Learn aerdynamics"},
        {id: 3, text: "Learn loops"}
      ];

    return {itemsFetched: itemsFetched,subObjectivesFetched: subObjectivesFetched}
  },

  render() {
    return (
      <div className="objectiveForm">
        <div className="form-wrapper">
          <Form
            method="POST"
            fields={this._formFields()}
            submitAction={this._handleForm}
            submitValue="Submit" />
          </div>
      </div> 
    )
  },

  _handleForm(formData) {
    console.log(formData)
  },

  _formFields() {
    let objectiveName = {}
    json.tupled(objectiveName, FCST.FIELD.ELEMENT, 'input')
    json.tupled(objectiveName, FCST.FIELD.TYPE, 'text')
    json.tupled(objectiveName, FCST.FIELD.PLACEHOLDER, "Objective's name")
    json.tupled(objectiveName, FCST.FIELD.NAME, 'objectiveName')
    json.tupled(objectiveName, FCST.FIELD.MANDATORY, true)
    
    let subObjectives = {}
    json.tupled(subObjectives, FCST.FIELD.ELEMENT, 'select')
    json.tupled(subObjectives, FCST.FIELD.NAME, 'subObjectives')
    json.tupled(subObjectives, FCST.FIELD.LABEL, 'Sub Objectives')
    json.tupled(subObjectives, FCST.FIELD.ID, 'subObjectives')
    json.tupled(subObjectives, FCST.FIELD.OPTIONS, this.state.subObjectivesFetched)
    json.tupled(subObjectives, FCST.FIELD.MULTIPLE, true)

    let items = {}
    json.tupled(items, FCST.FIELD.ELEMENT, 'select')
    json.tupled(items, FCST.FIELD.NAME, 'items')
    json.tupled(items, FCST.FIELD.LABEL, 'Items')
    json.tupled(items, FCST.FIELD.ID, 'items')
    json.tupled(items, FCST.FIELD.OPTIONS, this.state.itemsFetched)
    json.tupled(items, FCST.FIELD.MANDATORY, true)
    json.tupled(items, FCST.FIELD.MULTIPLE, true)

    return(
      [objectiveName, subObjectives, items]
    )
  }

})

export default objectiveForm