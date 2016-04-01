var gulp = require('gulp');
var newer = require('gulp-newer');
var changed = require('gulp-changed');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var sourcemaps = require('gulp-sourcemaps');
var del = require('del');
var path = require('path');
var debug = require('gulp-debug');
var paths = {};

paths.requirejs = {
    src: 'src/main/resources/static/lib/requirejs/require.js',
    dst: 'src/main/resources/static/lib/requirejs/'
}
gulp.task('require.min.js', function(){
    return gulp.src(paths.requirejs.src)
        .pipe(newer(path.join(paths.requirejs.dst,'require.min.js')))
        .pipe(sourcemaps.init())
        .pipe(uglify())
        .pipe(concat('require.min.js'))
        .pipe(sourcemaps.write('.'))
        .pipe(gulp.dest(paths.requirejs.dst));
});

gulp.task('clean-min-js', function(){
    del([
        path.join(paths.requirejs.dst, 'require.min.js'),
        path.join(paths.requirejs.dst, 'require.min.js.map'),
    ]);
});

gulp.task('build', [ 'require.min.js' ]);

gulp.task('clean', ['clean-min-js']);

paths.static = {
    src: 'src/main/resources/static/**/*',
    dst: 'build/resources/main/static/'
}
gulp.task('hotswap-static', function(){
    return gulp.src(paths.static.src)
        .pipe(changed(paths.static.dst))
        .pipe(debug({ title: 'hotswap detected : ' }))
        .pipe(gulp.dest(paths.static.dst));
});

paths.templates = {
    src: 'src/main/resources/templates/**/*',
    dst: 'build/resources/main/templates/'
}
gulp.task('hotswap-template', function(){
    return gulp.src(paths.templates.src)
        .pipe(changed(paths.templates.dst))
        .pipe(debug({ title: 'hotswap detected : ' }))
        .pipe(gulp.dest(paths.templates.dst));
});

gulp.task('watch', function(){
    gulp.watch([paths.static.src, paths.templates.src], ['hotswap-static', 'hotswap-template']);
});

gulp.task('default', ['build']);
