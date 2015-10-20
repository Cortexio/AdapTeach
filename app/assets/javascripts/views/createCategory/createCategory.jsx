import React from 'react'

var formCreateCategory = React.createClass({
	render(){
		<div classname="component">
			<form method="post" action"#" /*modify acion*/>
					<h3>Create a category</h3>
					<input type="text" name="name" id="name" value="name"/>
					<select name="pays"> 
						<option value="Parent Category" selected="selected">Parent Category </option>
						<option value="Cat1">Cat1 </option>
						<option value="Cat2">Cat2 </option>
						<option value="Cat3">Cat3 </option>
						<option value="Cat4">Cat4 </option> 
						<option value="Cat5">Cat5 </option>
						<option value="Cat6">Cat6 </option>
						<option value="Cat7">Cat7 </option>
					</select>
				</form>
			</div>
	}
});

export default formCreateCategory;