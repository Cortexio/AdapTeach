export default {
  help() {

    Object.prototype.forEach = function (callback) {
      var i = 0
      for (var prop in this) {
        if(this.propertyIsEnumerable(prop)) {
          callback.call(this, this[prop], i, this)
          i++
        }
      }
    }

    Object.prototype.tupled = function(key, value) {
      this[key] = value
      return this
    }

    Object.prototype.merge = function(obj) {
      for (var key in obj) {
        if(obj.propertyIsEnumerable(key)) this[key] = obj[key];
        return this;
      }
    }
  }
}