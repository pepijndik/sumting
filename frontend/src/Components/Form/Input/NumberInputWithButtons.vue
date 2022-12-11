<template>
  <div class="custom-number-input h-10 w-32">
    <label :for="id" class="w-full text-gray-700 text-sm font-semibold" v-text="placeholder">
    </label>
    <div class="flex flex-row h-10 w-full rounded-lg relative bg-transparent mt-1">
      <button @click="decrement" class=" bg-yInMnBlue text-candyPink hover:text-white hover:bg-candyPink h-full w-20 rounded-l cursor-pointer outline-none">
        <span class="m-auto text-2xl font-thin">−</span>
      </button>
      <input type="number"
             :id="id"
             :min="min"
             :max="max"
             :step="step"
              v-model="data"
             :name="name"
              v-on:change="$emit('update', data);"
             class="outline-none focus:outline-none text-center w-full bg-gray-300 font-semibold text-md hover:text-black focus:text-black  md:text-basecursor-default flex items-center text-gray-700  outline-none" />
      <button @click="increment" class="bg-yInMnBlue text-candyPink hover:text-white hover:bg-candyPink h-full w-20 rounded-r cursor-pointer">
        <span class="m-auto text-2xl font-thin">+</span>
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: "NumberInputWithButtons",
  props: {
    id: {
      type: String,
      required: true,
    },
    name: {
      type: String,
      default: "NumberInputWithButtons"
    },
    value: {
      type: Number,
      default: 0
    },
    min: {
      type: Number,
      default: 0
    },
    max: {
      type: Number,
      default: 100
    },
    step: {
      type: Number,
      default: 1
    },
    placeholder: {
      type: String,
      default: "NumberInputWithButtons"
    }
  },
  methods: {
    increment() {
      this.data = this.data + this.step;
    },
    decrement() {
      this.data = this.data - this.step;
    }
  },
  data() {
    return {
      data: this.value
    };
  },
  watch: {
    value: {
      handler: function (value) {
        //Only update the value if it is different from the current value
        if(value !== this.data){
          this.data = value;
        }
      },
      immediate: true
    }
  }

}
</script>

<style scoped>

</style>