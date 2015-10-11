var gulp = require('gulp');
var uglify = require('gulp-uglify');
var sass = require('gulp-sass');
var concat = require('gulp-concat');
var autoprefixer = require('gulp-autoprefixer');
var minifyCSS = require('gulp-minify-css');
var rename = require('gulp-rename'); 
var browserify = require('browserify');
var babelify = require('babelify');
var source = require('vinyl-source-stream');
var buffer = require('vinyl-buffer')

gulp.task('build-jsx', function () {
  browserify({
    entries: './app/assets/javascripts/app.js',
    extensions: ['.jsx'],
    debug: true
  })
  .transform(babelify)
  .bundle()
  .pipe(source('bundle.js'))
  .pipe(rename('adapteach.js'))
  .pipe(gulp.dest('./public/javascripts/'))
  .pipe(buffer())
  .pipe(uglify())
  .pipe(rename('adapteach.min.js'))
  .pipe(gulp.dest('./public/javascripts/'));
});

gulp.task('watch-jsx', function() {
    gulp.watch(['./app/assets/javascripts//**/*.jsx'], ['build-jsx']);
});

gulp.task('build-sass', function () {
    gulp.src('./app/assets/stylesheets/**/*.sass')
    .pipe(sass())
    .pipe(autoprefixer({
        browsers: ['last 3 versions'],
        cascade: false}))
    .pipe(gulp.dest('./public/stylesheets/'))
    .pipe(minifyCSS())
    .pipe(rename({
        suffix: '.min'
    }))
    .pipe(gulp.dest('./public/stylesheets/'))
});

gulp.task('watch-sass', function() {
    gulp.watch(['./app/assets/stylesheets/**/*.sass'], ['build-sass']);
});

gulp.task('build', ['build-sass', 'build-jsx']);

gulp.task('default', ['watch-sass', 'watch-jsx']);