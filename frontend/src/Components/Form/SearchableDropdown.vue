<template>
  <div class="dropdown" v-if="options">
    <!-- Dropdown Input -->

    <div class="flex flex-col lg:mr-16">
      <div class="relative">
        <div
          class="absolute text-gray-300 flex items-center pl-4 h-full cursor-pointer"
        >
          <svg
            class="fill-gray-300"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 487.95 487.95"
            width="17"
            height="17"
            xml:space="preserve"
          >
            <g>
              <g>
                <path
                  d="M481.8,453l-140-140.1c27.6-33.1,44.2-75.4,44.2-121.6C386,85.9,299.5,0.2,193.1,0.2S0,86,0,191.4s86.5,191.1,192.9,191.1
                c45.2,0,86.8-15.5,119.8-41.4l140.5,140.5c8.2,8.2,20.4,8.2,28.6,0C490,473.4,490,461.2,481.8,453z M41,191.4
                c0-82.8,68.2-150.1,151.9-150.1s151.9,67.3,151.9,150.1s-68.2,150.1-151.9,150.1S41,274.1,41,191.4z"
                />
              </g>
            </g>
          </svg>
        </div>
        <input
          class="rounded text-yInMnBlue focus:outline-none dark:border-gray-700 bg-white font-normal w-full sm:w-80 h-10 flex focus:border-yInMnBlue focus:border items-center pl-10 text-sm border-gray-300 focus:rounded-none focus:rounded-t-md border shadow font-inter"
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
    <div
      class="dropdown-one w-full sm:w-80 rounded-b-md outline-none bg-white relative mt-0 shadow-md"
    >
      <!-- Dropdown content -->
      <div
        class="w-full px-3 py-2 absolute rounded-b top-0 right-0 bg-white shadow-lg z-10 overflow-y-scroll max-h-32 border-yInMnBlue border-0 border-b border-l border-r"
        v-show="optionsShown"
      >
        <div
          class="flex items-center justify-between text-gray-600 hover:bg-champagnePink hover:text-gray-800 p-3 hover:cursor-default z-10 border-gray-300 border-0 border-b"
          @mousedown="selectOption(option)"
          v-for="(option, index) in filteredOptions"
          :key="index"
        >
          <div class="flex gap-2">
            <slot class="">
              <div
                v-if="optionHasIcon"
                :style="{ 'background-image': `url(${option.imgSmall})` }"
                class="w-7 h-5 bg-cover bg-center mx-3"
              ></div>
              <FileIcon v-if="!optionHasIcon" />
            </slot>
            <p class="">{{ this.populatefields(option) }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FileIcon from "@/Components/SvgIcons/FileIcon";

export default {
  name: "SearchableDropdown",
  components: { FileIcon },
  template: "Dropdown",

  props: {
    icon: {
      type: Boolean,
      default: true,
    },
    text: {
      type: Array,
      default: Array,
      note: 'Options to displayed text for selected',
    },
    name: {
      type: String,
      required: false,
      default: "dropdown",
      note: "Input name",
    },
    options: {
      type: Array,
      required: true,
      default: Array,
      note: "Options of dropdown. An array of options with id and name",
    },
    fields: {
      type: Array,
      required: false,
      // eslint-disable-next-line vue/require-valid-default-prop
      default: ["description"],
      note: "Possible fields options",
    },
    cleanAfterSelect: {
      type: Boolean,
      required: false,
      default: false,
      note: 'Clean search after select an option'
    },
    primarykey: {
      type: String,
      required: false,
      default: null,
      note: 'Primary key of the object'
    },
    placeholder: {
      type: String,
      required: false,
      default: "Please select an option",
      note: "Placeholder of dropdown",
    },
    disabled: {
      type: Boolean,
      required: false,
      default: false,
      note: "Disable the dropdown",
    },
    return: {
      type: String,
      required: false,
      default: 'object',
      note: 'Choose what to return, might be the primary key or the object itself'
    },

    maxItem: {
      type: Number,
      required: false,
      default: 6,
      note: "Max items showing",
    },
    optionHasIcon: {
      type: Boolean,
      required: false,
      default: false,
      note: "Option has icon",
    },
  },
  data() {
    return {
      selected: {},
      optionsShown: false,
      searchFilter: "",
    };
  },
  created() {
    this.$emit("selected", this.selected);
  },
  computed: {
    filteredOptions() {
      const regOption = new RegExp(this.searchFilter, 'ig');
      return this.options.filter(option => {
        return this.fields.some(field => {
          return regOption.test(option[field]);
        });
      });
    },
  },
  methods: {
    populatefields(option) {
      var finalString = "";
      this.fields.forEach(field => {
        const extractField = this.extractFieldValue(option, field);
        if (extractField) {
          finalString += extractField;
        }
        //Check if not the last field then append space with separator
        if (this.fields.indexOf(field) !== this.fields.length - 1 && extractField != null) {
          finalString += " | "
        }
      });
      return finalString;
    },
    extractFieldValue(option, prop) {
      var arr = prop.split(".");
      while (arr.length && (option = option[arr.shift()])) ;
      return option;
    },
    selectOption(option) {
      this.selected = option;
      this.optionsShown = false;
      if (this.text.length >= 1) {
        var finalString = "";
        this.text.forEach((field) => {
          const extractField = this.extractFieldValue(option, field);
          if (extractField) {
            finalString += extractField;
          }
          //Check if not the last field then append space with separator
          if (this.fields.indexOf(field) <= this.text.length - 1 && extractField != null) {
            finalString += " | "
          }
        });
        if (!this.cleanAfterSelect) {
          this.searchFilter = finalString;
        }
      }

      if (!this.cleanAfterSelect) {
        if (this.primarykey != null && this.text.length <= 0) {
          this.searchFilter = this.selected[this.primarykey];
        } else if (this.primarykey == null || this.text.length <= 0) {
          this.searchFilter = this.selected[this.fields[0]]; //Set the search filter to the first field
        }
      }
      this.emitSelect();
    },
    emitSelect() {
      //Choose what to return
      switch (this.return) {
        case 'primarykey':
          this.$emit('selected', this.selected[this.primarykey]);
          break;
        case 'object':
          this.$emit('selected', this.selected);
          break;
        default:
          this.$emit('selected', this.selected[this.primarykey]);
          break;
      }
      console.log(this.selected[this.primarykey]);
    },
    showOptions() {
      if (!this.disabled) {
        this.searchFilter = "";
        this.optionsShown = true;
      }
    },
    exit() {
      this.optionsShown = false;
      // this.selectOption(this.selected);
    },
    // Selecting when pressing Enter
    keyMonitor: function (event) {
      if (event.key === "Enter" && this.filteredOptions[0])
        this.selectOption(this.filteredOptions[0]);
    },
  },
  watch: {
    searchFilter() {
      if (this.filteredOptions.length === 0) {
        this.selected = {};
      } else {
        this.selected = this.filteredOptions[0];
        this.emitSelect();
      }

    }
  }
};
</script>

<style scoped></style>
