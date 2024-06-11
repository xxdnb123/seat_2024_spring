<template>
  <div id="app">
    <!-- <p class="tab"> -->
      <!-- <router-link to="/all_students" class="btn btn-primary" -->
        <!-- >学生管理</router-link -->
      <!-- > -->
      <!-- <router-link to="/all_studyrooms">自习室管理</router-link> -->
    <!-- </p> -->
    <router-view></router-view>
  </div>
</template>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    /*margin-top: 60px;*/
  }
  body{
    margin: 0px;
    padding: 0px;
  }
</style>

<script>
export default {
  name: "App",
};
</script>
