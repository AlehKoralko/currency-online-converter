<template>
  <div id="app">
    <Content/>
  </div>
</template>

<script>
import HelloWorld from './components/HelloWorld'
import Content from './components/Content'
import MyForm from './components/MyForm'

export default {
  name: 'App',
  components: {
    HelloWorld,
    Content,
    MyForm
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
