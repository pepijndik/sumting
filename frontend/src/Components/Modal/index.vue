<template>
  <div
    v-if="modalOpen"
    class="py-48 bg-gray-700/50 dark:bg-gray-900/50 transition duration-150 ease-in-out z-10 absolute top-0 right-0 bottom-0 left-0"
  >
    <div role="alert" class="container mx-auto w-11/12 md:w-2/3 max-w-lg">
      <div
        class="relative py-8 px-8 md:px-16 bg-white dark:bg-gray-800 dark:border-gray-700 shadow-md rounded border border-gray-400"
      >
        <button
          class="focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-500 rounded-md cursor-pointer absolute top-0 right-0 mt-4 mr-5 text-gray-400 hover:text-gray-600 dark:hover:text-gray-500 transition duration-150 ease-in-out"
          aria-label="Close"
          role="button"
          @click="toggle"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            class="icon icon-tabler icon-tabler-x"
            width="20"
            height="20"
            viewBox="0 0 24 24"
            stroke-width="2.5"
            stroke="currentColor"
            fill="none"
            stroke-linecap="round"
            stroke-linejoin="round"
          >
            <path stroke="none" d="M0 0h24v24H0z" />
            <line x1="18" y1="6" x2="6" y2="18" />
            <line x1="6" y1="6" x2="18" y2="18" />
          </svg>
        </button>
        <div class="w-full flex justify-center text-green-400 mb-4">
          <img
            :src="require(`@/Assets/img/icons/${source}`)"
            alt="Dropdown icon"
            width="64"
            class="mr-2"
          />
        </div>
        <slot name="body">
          <h1
            tabindex="0"
            class="focus:outline-none text-center text-gray-800 dark:text-gray-100 font-lg font-bold tracking-normal leading-tight mb-4"
          >
            Titel
          </h1>
          <p
            tabindex="0"
            class="focus:outline-none mb-5 text-sm text-gray-600 dark:text-gray-400 text-center font-normal"
          >
            Beschrijving
          </p>
        </slot>
        <div class="flex items-center justify-center w-full">
          <button
            class=" text-white bg-yInMnBlue transition duration-150 hover:bg-yInMnBlue/50 border rounded px-4 sm:px-8 py-2 text-xs sm:text-sm mx-2"
            @click="toggle"
          >
            Cancel
          </button>
          <slot name="footer">
            <button
              class="transition duration-150 ease-in-out hover:bg-candyPink/50 bg-candyPink rounded text-white px-4 sm:px-8 py-2 text-xs sm:text-sm"
            >
              Yes
            </button>
          </slot>
          
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "index",

  props: {
    text: {
      type: Object,
      default: new Object({
        title: "title",
        description: "description",
        cancel: "cancel",
        oke: "oke",
      }),
    },
    open: {
      type: Boolean,
      default: false,
      editable: true,
      required: true,
    },
    callback: {
      type: Function,
    },
    source: {
      type: String,
      default: "dashboard.svg",
    },
  },
  watch: {
    open: {
      //handle when prop change
      handler(val, oldVal) {
        this.modalOpen = val;
      },
    },
  },
  data() {
    return {
      modalOpen: this.open,
    };
  },

  methods: {
    oke() {
      this.toggle();
      this.callback(true); //Fire callback
    },
    toggle() {
      this.modalOpen = !this.modalOpen;
      if (!this.modalOpen) {
        this.callback(false); //Fire callback When false
      }
    },
  },
};
</script>

<style scoped></style>
