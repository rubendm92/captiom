/*
Copyright (c) 2015 The Polymer Project Authors. All rights reserved.
This code may only be used under the BSD style license found at http://polymer.github.io/LICENSE.txt
The complete set of authors may be found at http://polymer.github.io/AUTHORS.txt
The complete set of contributors may be found at http://polymer.github.io/CONTRIBUTORS.txt
Code distributed by Google as part of the polymer project is also
subject to an additional IP rights grant found at http://polymer.github.io/PATENTS.txt
*/

'use strict';

// Include Gulp & tools we'll use
var gulp = require('gulp');
var $ = require('gulp-load-plugins')();
var del = require('del');
var runSequence = require('run-sequence');
var livereload = require('gulp-livereload');
var merge = require('merge-stream');
var path = require('path');

var AUTOPREFIXER_BROWSERS = [
  'ie >= 10',
  'ie_mob >= 10',
  'ff >= 30',
  'chrome >= 34',
  'safari >= 7',
  'opera >= 23',
  'ios >= 7',
  'android >= 4.4',
  'bb >= 10'
];

var PRODUCTION_PATH = '../out/production/captiom/server/site';

var styleTask = function (stylesPath, srcs) {
  return gulp.src(srcs.map(function(src) {
      return path.join('app', stylesPath, src);
    }))
    .pipe($.changed(stylesPath, {extension: '.css'}))
    .pipe($.autoprefixer(AUTOPREFIXER_BROWSERS))
    .pipe(gulp.dest('.tmp/' + stylesPath))
    .pipe($.cssmin())
    .pipe(gulp.dest('dist/' + stylesPath));
};

var jshintTask = function (src) {
  return gulp.src(src)
    .pipe($.jshint.extract()) // Extract JS from .html files
    .pipe($.jshint())
    .pipe($.jshint.reporter('jshint-stylish'))
    .pipe($.jshint.reporter('fail'));
};

var optimizeHtmlTask = function (src, dest) {
  var assets = $.useref.assets({searchPath: ['.tmp', 'app', 'dist']});

  return gulp.src(src)
    // Replace path for vulcanized assets
    .pipe($.if('*.html', $.replace('elements/elements.html', 'elements/elements.vulcanized.html')))
    .pipe(assets)
    // Concatenate and minify JavaScript
    .pipe($.if('*.js', $.uglify({preserveComments: 'some'})))
    // Concatenate and minify styles
    // In case you are still using useref build blocks
    .pipe($.if('*.css', $.cssmin()))
    .pipe(assets.restore())
    .pipe($.useref())
    // Minify any HTML
    .pipe($.if('*.html', $.minifyHtml({
      quotes: true,
      empty: true,
      spare: true
    })))
    // Output files
    .pipe(gulp.dest(dest));
};

gulp.task('copy-styles', function () {
  return styleTask('styles', ['**/*.css']);
});

gulp.task('copy-element-styles', function () {
  return styleTask('elements', ['**/*.css']);
});

gulp.task('transpile-js', function () {
  return gulp.src(['app/**/*.{js,html}'])
    .pipe($.sourcemaps.init())
    .pipe($.if('*.html', $.crisper())) // Extract JS from .html files
    .pipe($.if('*.js', $.babel()))
    .pipe($.sourcemaps.write('.'))
    .pipe(gulp.dest('.tmp/'))
    .pipe(gulp.dest('dist/'));
});

gulp.task('check-js', function () {
  return jshintTask([
      'app/scripts/**/*.js',
      'app/elements/**/*.js',
      'app/elements/**/*.html',
      'gulpfile.js'
    ]);
});

// Copy all files at the root level (app)
gulp.task('generate-dist-folder', function () {
  return gulp.src([
    'app/*',
    '!app/test'
  ], {
    dot: true
  }).pipe(gulp.dest('dist'));
});

gulp.task('copy-dependencies', function () {
  return gulp.src([
    'bower_components/**/*'
  ]).pipe(gulp.dest('dist/bower_components'));
});

gulp.task('copy-elements', function () {
  var elements = gulp.src(['app/elements/**/*.html',
                           'app/elements/**/*.css',
                           'app/elements/**/*.js'])
    .pipe(gulp.dest('dist/elements'));

  var vulcanized = gulp.src(['app/elements/elements.html'])
    .pipe($.rename('elements.vulcanized.html'))
    .pipe(gulp.dest('dist/elements'));

  return merge(elements, vulcanized);
});

// Copy all files to res directory from server module
gulp.task('copy-to-res', function () {
  return gulp.src([
    'dist/**/*'
  ], {
    dot: true
  }).pipe(gulp.dest('../server/res/site'));
});

gulp.task('copy-to-server', function () {
  return gulp.src([
    'dist/**/*'
  ], {
    dot: true
  }).pipe(gulp.dest(PRODUCTION_PATH));
});

gulp.task('copy-fonts', function () {
  return gulp.src(['app/fonts/**'])
    .pipe(gulp.dest('dist/fonts'));
});

gulp.task('copy-js', function () {
  return gulp.src(['app/**/*.js', '!app/{elements,test}/**/*.js']).pipe(gulp.dest('dist'));
});

gulp.task('copy-minified-html', function () {
  return optimizeHtmlTask(['app/**/*.html', '!app/{elements,test}/**/*.html'], 'dist');
});

gulp.task('vulcanize', function () {
  var DEST_DIR = 'dist/elements';
  return gulp.src('dist/elements/elements.vulcanized.html')
    .pipe($.vulcanize({
      stripComments: true,
      inlineCss: true,
      inlineScripts: true
    }))
    .pipe(gulp.dest(DEST_DIR));
});

gulp.task('rename-index', function () {
  return gulp.src('dist/index.build.html')
    .pipe($.rename('index.html'))
    .pipe(gulp.dest('dist/'));
});

gulp.task('remove-old-build-index', function (cb) {
  del('dist/index.build.html', cb);
});

// Clean output directory
gulp.task('clean', function (cb) {
  del(['.tmp', 'dist'], cb);
});

gulp.task('dev', ['copy-dev-app-to-res'], function() {
  livereload.listen({
    basePath: 'app'
  });

  gulp.watch(['app/index.html'], ['refresh']);
  gulp.watch(['app/{styles,elements}/**/*.css'], ['refresh']);
  gulp.watch(['app/{scripts,elements}/**/{*.js,*.html}'], ['refresh']);
});

gulp.task('refresh', function(cb) {
  runSequence('generate-dev-app', 'copy-to-server', 'reload', cb);
});

gulp.task('reload', function() {
  gulp.src(PRODUCTION_PATH + '/index.html').pipe(livereload());
});

gulp.task('copy-dev-app-to-res', function(cb) {
  runSequence('generate-dev-app', 'copy-to-res', cb);
});

gulp.task('generate-dev-app', ['clean'], function(cb) {
  runSequence(
    'generate-dist-folder', ['copy-styles', 'copy-dependencies', 'copy-elements', 'copy-element-styles', 'copy-fonts'],
    'check-js', 'transpile-js', 'copy-js', cb);
});

gulp.task('default', ['clean'], function (cb) {
  runSequence(
    'generate-dist-folder', ['copy-styles', 'copy-dependencies', 'copy-elements', 'copy-element-styles', 'copy-fonts'],
    'check-js', 'transpile-js',
    'copy-minified-html',
    'vulcanize','rename-index', 'remove-old-build-index',
    'copy-to-res',
    cb);
});
