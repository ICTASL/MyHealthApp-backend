# MyHealth - Management Portal - Vue.js

## Development Requirements
- Node.js
- NPM

## To Run the Portal without the Backend
### Project setup
```
npm install
```
#### Compiles and hot-reloads for development
```
npm run serve
```
#### Compiles and minifies for production
```
npm run build
```
#### Lints and fixes files
```
npm run lint
```

## To Run the Frontend Together with the Backend
- **First** run the backend
    - Open another terminal and,
    ```
    cd ../backend
    mvn spring-boot:run
    ```
- Then run the frontend using the command `npm run serve`

## Tips
- To avoid login in every time when writing the UI, comment the section 
```
meta: {
    requiresAuth: true
},
```
in `src/router/index.js`