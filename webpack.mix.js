let mix = require('laravel-mix');
const tailwindcss = require('tailwindcss');
require('laravel-mix-purgecss');

/*
 |--------------------------------------------------------------------------
 | Mix Asset Management
 |--------------------------------------------------------------------------
 |
 | Mix provides a clean, fluent API for defining some Webpack build steps
 | for your Laravel application. By default, we are compiling the Sass
 | file for your application, as well as bundling up your JS files.
 |
 */

mix
    .js('src/main/resources/js/alerts.js', 'src/main/resources/static/js/alerts.js')
    .js('src/main/resources/js/Case.js', 'src/main/resources/static/js/Case.js')
    .js('src/main/resources/js/dashboard.js', 'src/main/resources/static/js/dashboard.js')
    .postCss('src/main/resources/css/app.css', 'src/main/resources/static/css/app.css',[
        require('tailwindcss')('./covid-config.js')
    ])

