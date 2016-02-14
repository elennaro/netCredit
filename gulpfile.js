var gulp = require('gulp'),
    clean = require('gulp-clean'),
    concat = require('gulp-concat'),
    order = require("gulp-order"),
    concatCss = require('gulp-concat-css'),
    less = require('gulp-less'),
    minifyCSS = require('gulp-minify-css');

var OPTS = {
    jsAppName: 'app.js',
    cssAppName: 'app.css',
    src: {
        less: 'src/main/frontend/less/',
        templates: 'src/main/frontend/**/*.html',
        js: 'src/main/frontend/js/**/*.js',
        static_out: 'src/main/webapp/resources/'
    },
    libs: []
};

gulp.task('clean', function () {
    console.log('----------------------------------------');
    console.log('GULP CLEAN');
    console.log('----------------------------------------');

    return gulp.src([OPTS.src.static_out + "**", '!' + OPTS.src.static_out + "/.gitkeep"], {read: false}).pipe(clean());

});

gulp.task('styles', function () {
    return gulp.src(OPTS.src.less + "/*")
        .pipe(less())
        .pipe(concatCss(OPTS.cssAppName))
        .pipe(minifyCSS())
        .pipe(gulp.dest(OPTS.src.static_out));
});

gulp.task('scripts', function () {
    return gulp.src(OPTS.src.js)
        .pipe(order([
            'interceptors/**/*.js',
            'main.js',
            'config/**/*.js',
            'services/**/*.js',
            'models/**/*.js',
            'directives/**/*.js',
            'controllers/**/*.js',
            'run/**/*.js',
            '**/*.js'
        ]))
        .pipe(concat(OPTS.jsAppName))
        .pipe(gulp.dest(OPTS.src.static_out));
});

gulp.task('templates', function () {
    return gulp.src(OPTS.src.templates)
        .pipe(gulp.dest(OPTS.src.static_out));
});

gulp.task('watch', ['build'], function () {
    gulp.watch(OPTS.src.js, ['scripts']);
    gulp.watch(OPTS.src.less + '/**', ['styles']);
    gulp.watch(OPTS.src.templates, ['templates']);
});

gulp.task('build', ['styles', 'templates', 'scripts'], function () {
    console.log('----------------------------------------');
    console.log('GULP BUILD PROCESS FINISHED');
    console.log('----------------------------------------');
});

gulp.task('default', ['build']);