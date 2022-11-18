<template>
  <div class="dropdown" v-if="options">
    <!-- Dropdown Input -->

    <div class="flex flex-col lg:mr-16">
      <div class="relative">
        <div class="absolute text-gray-300 flex items-center pl-4 h-full cursor-pointer">
          <svg class="fill-gray-300" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 487.95 487.95" width="17"
               height="17" xml:space="preserve">
          <g>
            <g>
              <path d="M481.8,453l-140-140.1c27.6-33.1,44.2-75.4,44.2-121.6C386,85.9,299.5,0.2,193.1,0.2S0,86,0,191.4s86.5,191.1,192.9,191.1
                c45.2,0,86.8-15.5,119.8-41.4l140.5,140.5c8.2,8.2,20.4,8.2,28.6,0C490,473.4,490,461.2,481.8,453z M41,191.4
                c0-82.8,68.2-150.1,151.9-150.1s151.9,67.3,151.9,150.1s-68.2,150.1-151.9,150.1S41,274.1,41,191.4z"/>
            </g>
          </g>
          </svg>
        </div>
        <input
            class="text-yInMnBlue focus:outline-none dark:border-gray-700 bg-white font-normal w-full sm:w-80 h-10 flex focus:border-candyPink  focus:border-0 focus:border-t-2 focus:border-l-2 focus:border-r-2 focus:border-b-1
            items-center pl-10 text-sm border-gray-300 rounded-t-md border shadow font-inter"
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
    <div class="dropdown-one w-full sm:w-80 rounded-b-md outline-none bg-white relative mt-0 shadow-md">
      <!-- Dropdown content -->
      <div
          class="rounded w-full px-3 py-2 absolute top-1 right-0 bg-white shadow-lg z-10 overflow-y-scroll max-h-32 border-candyPink  border-0 border-b-2 border-l-2 border-r-2"
          v-show="optionsShown">
        <div
            class="flex items-center justify-between hover:bg-gray-100 rounded text-gray-600 hover:text-gray-800 p-3
            hover:font-bold hover:cursor-default z-10"
            @mousedown="selectOption(option)"
            v-for="(option, index) in filteredOptions"
            :key="index">
          <div class="flex">
            <slot class="">
<!--              <user-icon v-if="option.user_type = PERSON "/>-->
<!--              <user-icon v-if="option.user_type = undefined"/>-->
              <company-icon v-if="option.user_type = BUSINESS"/>
<!--              <company-icon/>-->
              <user-icon/>
            </slot>
            <p class="">{{ this.populatefields(option) }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import userIcon from "@/Components/SvgIcons/userIcon";
import CompanyIcon from "@/Components/SvgIcons/CompanyIcon";


export default {
  name: 'SearchableDropDown',
  components: {userIcon, CompanyIcon},
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
    fields: {
      type: Array,
      required: false,
      // eslint-disable-next-line vue/require-valid-default-prop
      default: ['description'],
      note: 'Possible fields options'
    },
    primaryKey: {
      type: String,
      required: false,
      default: 'id',
      note: 'Primary key of the object'
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
        //Double For loop to find the option in the fields
        this.fields.forEach(field => {
          if (this.searchFilter.length < 1 || option[field].match(regOption)) {
            if (filtered.length < this.maxItem) filtered.push(option);
          }
        });

      }

      return filtered;
    },
  },
  methods: {
    populatefields(option) {
      var finalString = "";
      this.fields.forEach(field => {
        finalString += this.extractFieldValue(option,field);
        //Check if not the last field then append space with separator
        if(this.fields.indexOf(field) !== this.fields.length -1)
        {
          finalString += " | "
        }
      });
      return finalString
    },
    extractFieldValue(option,prop) {
      // eslint-disable-next-line no-prototype-builtins
      if(Object.hasOwn(option,prop))
      {
        return option[prop];
      }
    },
    selectOption(option) {
      this.selected = option;
      this.optionsShown = false;
      console.log(this.selected[this.fields[0]]);
      this.searchFilter = this.selected[this.fields[0]]; //Set the search filter to the first field
      this.$emit('selected', this.selected);
    },
    showOptions() {
      if (!this.disabled) {
        this.searchFilter = '';
        this.optionsShown = true;
      }
    },
    exit() {
      if (!this.selected[this.primaryKey]) {
        this.selected = {};
        this.searchFilter = '';
      } else {
        this.searchFilter = this.selected[this.fields[0]];
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
