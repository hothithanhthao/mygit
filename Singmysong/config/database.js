if(process.env.NODE_ENV === 'production'){
  module.exports = {mongoURI: 'mongodb://<dbuser>:<dbpassword>@ds231529.mlab.com:31529/singmysong'}
} else {
  module.exports = {mongoURI: 'mongodb://localhost/vidjot-dev'}
}