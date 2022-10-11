<template>
  <div class="mx-auto container bg-white dark:bg-gray-800 dark:bg-gray-800 shadow rounded">
    <Header/>
    <div class="w-full overflow-x-scroll xl:overflow-x-hidden">
      <table class="min-w-full bg-white dark:bg-gray-800">
        <thead>
          <RowHeader>

          </RowHeader>
        </thead>
        <tbody>
        <Row v-for="item in items"  :key="item.id" :item="item"/>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import Header from "@/Components/Table/Header";
import Row from "@/Components/Table/Row";
export default {
  name: "index",
  components: {Header,Row}
}
</script>

<style scoped>

</style>