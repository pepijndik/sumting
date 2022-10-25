<template>
  <div class="dropdown" v-if="options">
    <!-- Dropdown Input -->

    <div class="flex flex-col lg:mr-16">
      <div class="relative">
        <div class="absolute text-gray-600 dark:text-gray-400 flex items-center pl-4 h-full cursor-pointer">
          <img class="h-6 w-6 fill-shadow" :src="require(`@/Assets/img/icons/search.svg`)" alt="search icon">
        </div>
        <input
            class="text-gray-600 dark:text-gray-400 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-candyPink dark:border-gray-700 dark:bg-gray-800 bg-white font-normal w-64 h-10 flex items-center pl-12 text-sm border-gray-300 rounded border shadow"
            :name="name"
            @focus="showOptions()"
            @blur="exit()"
            @keyup="keyMonitor"
            v-model="searchFilter"
            :disabled="disabled"
            :placeholder="placeholder"
        />
      </div>
    </div>
    <div class="dropdown-one w-64 rounded outline-none bg-white relative mt-2 shadow-md">
      <!-- Dropdown content -->
      <div class="rounded w-full px-3 py-2 absolute top-4 right-0 bg-white shadow-lg"
           v-show="optionsShown">
        <div
            class="flex items-center justify-between hover:bg-gray-100 rounded text-gray-600 hover:text-gray-800 p-3 hover:font-bold hover:cursor-default"
            @mousedown="selectOption(option)"
            v-for="(option, index) in filteredOptions"
            :key="index">
          {{ option.name || option.id || '-' }}
        </div>
      </div>
    </div>
  </div>

</template>

<script>
export default {
  name: 'SearchableDropDown',
  template: 'Dropdown',
  props: {
    name: {
      type: String,
      required: false,
      default: 'dropdown',
      note: 'Input name'
    },
    options: {
      type: Array,
      required: true,
      default: Array,
      note: 'Options of dropdown. An array of options with id and name',
    },
    placeholder: {
      type: String,
      required: false,
      default: 'Please select an option',
      note: 'Placeholder of dropdown'
    },
    disabled: {
      type: Boolean,
      required: false,
      default: false,
      note: 'Disable the dropdown'
    },
    maxItem: {
      type: Number,
      required: false,
      default: 6,
      note: 'Max items showing'
    }
  },
  data() {
    return {
      selected: {},
      optionsShown: false,
      searchFilter: ''
    }
  },
  created() {
    this.$emit('selected', this.selected);
  },
  computed: {
    filteredOptions() {
      const filtered = [];
      const regOption = new RegExp(this.searchFilter, 'ig');
      for (const option of this.options) {
        if (this.searchFilter.length < 1 || option.name.match(regOption)) {
          if (filtered.length < this.maxItem) filtered.push(option);
        }
      }
      return filtered;
    }
  },
  methods: {
    selectOption(option) {
      this.selected = option;
      this.optionsShown = false;
      this.searchFilter = this.selected.name;
      this.$emit('selected', this.selected);
    },
    showOptions() {
      if (!this.disabled) {
        this.searchFilter = '';
        this.optionsShown = true;
      }
    },
    exit() {
      if (!this.selected.id) {
        this.selected = {};
        this.searchFilter = '';
      } else {
        this.searchFilter = this.selected.name;
      }
      this.$emit('selected', this.selected);
      this.optionsShown = false;
    },
    // Selecting when pressing Enter
    keyMonitor: function (event) {
      if (event.key === "Enter" && this.filteredOptions[0])
        this.selectOption(this.filteredOptions[0]);
    }
  },
  watch: {
    searchFilter() {
      if (this.filteredOptions.length === 0) {
        this.selected = {};
      } else {
        this.selected = this.filteredOptions[0];
      }
      this.$emit('filter', this.searchFilter);
    }
  }
};
</script>


<style scoped>

</style>
