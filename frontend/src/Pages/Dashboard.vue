<template>
  <h1>Dashboard</h1>
  <SearchableDropDown
      :options="[{ id: 1, name: 'Option 1'}, { id: 2, name: 'Option 2'}]"
      v-on:selected="validateSelection"
      v-on:filter="getDropdownValues"
      :disabled="false"
      autocomplete="off"
      :maxItem="10"
      placeholder="Please select an option">
  </SearchableDropDown>
</template>

<script>
import SearchableDropDown from "@/Components/Form/SearchableDropDown";

export default {
  name: "Dashboard",
  components: {
    SearchableDropDown
  },
  metaInfo: {
    title: 'Dashboard',
  }
}
</script>

<style scoped>

</style>