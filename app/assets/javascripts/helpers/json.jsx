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
  }
}