export default {  
  /* Object util functions */
  /*
  * Extends a base object with all the key/values of another.
  */
  extend(obj, other) {
    let result = this.copy(obj)
    Object.keys(other).forEach(key => result[key] = other[key])
    return result
  },
  /*
  * Add tuple in object
  */
  tupled(obj, key, value) {
    let result = obj
    result[key] = value
    return result
  },
  /*
  * Creates a shallow copy of an object.
  */
  copy(obj) {
    let result = {}
    Object.keys(obj).forEach(key => result[key] = obj[key])
    return result
  },
  /*
  * Returns a new object with all its key/values mapped.
  * A tuple (Array of size 2) must be returned from the map function.
  */
  map(obj, fn) {
    let result = {}
    for (let key in obj) {
      let [newKey, newValue] = fn(key, obj[key])
      result[newKey] = newValue
    }
    return result
  },
  /*
  * Returns a new object with all its keys mapped.
  */
  mapKeys(obj, fn) {
    let result = {}
    for (let key in obj) {
      let newKey = fn(key, obj[key])
      result[newKey] = obj[key]
    }
    return result
  },
  /*
  * Returns a new object with all its values mapped.
  */
  mapValues(obj, fn) {
    let result = {}
    for (let key in obj) {
      result[key] = fn(key, obj[key])
    }
    return result
  },
  /*
  * Returns a new object with only the original object's keys that passed the predicate.
  */
  filter(obj, predicate) {
    let result = {}
    Object.keys(obj).forEach(key => {
      if (predicate(key, obj[key])) result[key] = obj[key]
    })
    return result
  },
  /*
  * Returns a new object with the passed key removed.
  */
  omit(obj, ...props) {
    let result = this.copy(obj)
    props.forEach(prop => delete result[prop])
    return result
  },
  /*
  * Creates a JS Set (i.e {fizz: 1, buzz: 1}) from a list of arguments
  * The keys are the toString'ed arguments while the values are truthy.
  */
  Set() {
    let set = {}
    for (let i = 0; i < arguments.length; i++) {
      set[arguments[i]] = 1
    }
    return set
  },
  /*
  * Returns a new Array with all the values found in the passed object.
  */
  values(obj) {
    let result = []
    Object.keys(obj).forEach(key => result.push(obj[key]))
    return result
  },
  /*
  * Reads a property at the specified path string (e.g 'prop.nested.value') or undefined if the path is invalid.
  */
  read(obj, path) {
    return path.split('.').reduce((acc, val) => {
      if (!acc) return undefined
      else return acc[val]
    }, obj)
  },
  /* Returns the size of the object */
  size(obj) {
    return Object.keys(obj).length
  },
  /**
  * Returns whether the passed reference is an object.
  */
  isObject(x) {
    return x && typeof x == 'object'
  },
  /**
  * Returns whether the passed reference is a function.
  */
  isFunction(x) {
    return Object.prototype.toString.call(x) === '[object Function]'
  }
}