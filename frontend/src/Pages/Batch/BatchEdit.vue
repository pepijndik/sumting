<template>
  <div class="flex flex-col sm:flex-row sm:justify-between xl:justify-start">
    <div>
      <p class="font-inter text-yInMnBlue mb-1">Project</p>
      <p>{{ projectDescription }}</p>
    </div>
  </div>

  <div class="mt-3">
    <p class="font-inter text-yInMnBlue mb-1">Description</p>
    <ckeditor class="block w-full mt-1 rounded-md lg:prose-xl"
              :editor="editor" v-model="description" :config="editorConfig" tag-name="textarea"></ckeditor>
  </div>

  <div class="mt-3">
    <p class="font-inter text-yInMnBlue mb-1">Orderlines list</p>
    <div
        class="h-80 text-sm border-gray-300 rounded border shadow overflow-y-scroll overflow-x-hidden
    scrollbar-thin scrollbar-thumb-yInMnBlue">
      <div class="p-3 flex gap-2 border-0 border-b sm:justify-between">
        <div class="relative sm:w-80">
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
              class="rounded text-yInMnBlue focus:outline-none dark:border-gray-700 bg-white font-normal w-full
            h-10 flex focus:border-yInMnBlue focus:border items-center pl-10 text-sm border-gray-300
            border font-inter"
              v-model="searchKeyWord"
              placeholder="Search for your orderline"
          />
        </div>
        <div @click="changeListOrder()" class="flex px-2 font-inter text-yInMnBlue border border-gray-300 rounded
      cursor-pointer">
          <p v-if="searchOrderline" class="flex m-auto">
            <span>Old</span>
            <svg class="fill-yInMnBlue rotate-90 m-auto" width="17" height="17" viewBox="0 0 36 36" version="1.1"  preserveAspectRatio="xMidYMid meet" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
              <path d="M27.66,15.61,18,6,8.34,15.61A1,1,0,1,0,9.75,17L17,9.81V28.94a1,1,0,1,0,2,0V9.81L26.25,17a1,1,0,0,0,1.41-1.42Z" class="clr-i-outline clr-i-outline-path-1"></path>
            </svg>
            <span>New</span>
          </p>
          <p v-if="!searchOrderline" class="flex m-auto">
            <span>Old</span>
            <svg class="fill-yInMnBlue rotate-270 m-auto" width="17" height="17" viewBox="0 0 36 36" version="1.1"  preserveAspectRatio="xMidYMid meet" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
              <path d="M27.66,15.61,18,6,8.34,15.61A1,1,0,1,0,9.75,17L17,9.81V28.94a1,1,0,1,0,2,0V9.81L26.25,17a1,1,0,0,0,1.41-1.42Z" class="clr-i-outline clr-i-outline-path-1"></path>
            </svg>
            <span>New</span>
          </p>
        </div>
      </div>
      <form class="px-3 w-full h-15 lg:h-10 items-center text-sm snap-y snap-mandatory"
            v-for="(orderline, index) in computedObj"
            :key="orderline.id"
      >
        <div class="w-full lg:h-10 md:h-20 flex text-sm border-gray-300 border-b font-Alatsi items-center">
          <div class="m-2 flex lg:flex-row flex-wrap">
            <div class="h-5 lg:h-10 items-center flex w-[26px] text-xs md:text-sm border-r-2 mr-1">
              <input type="checkbox"
                     v-model="checkedOrderlines"
                     :value="orderline"
                     :checked="index < this.batch.data.orderLines.length"
                     @change="handleCheckboxChange">
            </div>
            <div class="float-left items-left flex w-[80px] md:w-[100px] lg:w-[200px] items-center border-r-2 pr-1 mr-1">
              <img
                  :src="require(`@/Assets/img/icons/file.svg`)"
                  alt="File icon"
                  width="24"
              />
              <p class="text-yInMnBlue">ID {{ orderline.id }}</p>
            </div>
            <div class="h-5 lg:h-10 items-center flex lg:w-[200px] md:w-[180px] w-[95px] text-xs md:text-sm border-r-2 mr-1">
              <p class="text-candyPink">{{ orderline.notes }}</p>
            </div>
            <div class="h-5 lg:h-10 items-center flex lg:w-[150px] md:w-[120px] w-[95px] font-Alatsi text-xs md:text-sm">
              <p class="text-yInMnBlue">Date:&nbsp;</p>
              <p class="text-candyPink">{{ orderline.loadedDate }}</p>
            </div>
          </div>
        </div>
      </form>
    </div>
    <div class="flex flex-col sm:flex-row sm:gap-5">
      <button
          @click="backToView"
          class="mt-6 w-full sm:w-80 bg-yInMnBlue transition duration-150 ease-in-out hover:bg-indigo-600 rounded
          text-white font-inter px-8 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-600">
        Cancel
      </button>
      <button
          v-on:click="editBatch"
          class="mt-6 w-full sm:w-80 bg-candyPink transition duration-150 ease-in-out hover:bg-indigo-600 rounded
    text-white font-inter px-8 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-600">
        Edit batch
      </button>
    </div>
  </div>
</template>

<script>
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import {ref} from "@vue/reactivity";
export default {
  name: "BatchEdit",
  inject: ["ProjectApi", "OrderApi", "BatchApi"],
  data() {
    return {
      id: this.$route.params.id,
      editor: ClassicEditor,
      description: ref(''),
      editorConfig: {

        language: 'nl',
        toolbar: {
          items: [
            'heading',
            '|',
            'bold',
            'italic',
            'link',
            'bulletedList',
            'numberedList',
            'blockQuote',
            'insertTable',
            'mediaEmbed',
            'undo',
            'redo'
          ]
        },
      },
      orderlines: [],
      checkedOrderlines: [],
      remainingOrderlines: [],
      batch: null,
      batchProject: null,
      projectDescription: '',
      searchKeyWord: '',
      searchOrderline: true,
    }
  },
  async created() {
    this.batch = await this.BatchApi.findOne(this.id);
    this.batchProject = await this.ProjectApi.findOne(this.batch.data.projectKey);
    this.remainingOrderlines = await this.OrderApi.getAllOrderlinesByProductId(
        this.batch.data.orderLines.at(0).product.id);

    for (let i = 0; i < this.batch.data.orderLines.length; i++) {
      this.orderlines.push(this.batch.data.orderLines.at(i));
      this.checkedOrderlines.push(this.batch.data.orderLines.at(i));
    }

    for (let i = 0; i < this.remainingOrderlines.length; i++) {
      this.orderlines.push(this.remainingOrderlines.at(i));
    }

    this.fillBatchData();
  },
  watch: {
    orderlines() {
      this.orderlines.sort((a,b) => {
        return a.loadedDate.localeCompare(b.loadedDate)
      });
    },
    checkedOrderlines() {
      console.log(this.checkedOrderlines);
    }
  },
  computed: {
    computedObj() {
      const results = [];
      const regKeyWord = new RegExp(this.searchKeyWord, 'ig');

      if (this.searchKeyWord === '') {
        return this.orderlines;
      }

      for (const orderline of this.orderlines) {
        const string = orderline.id.toString();
        if (string.match(regKeyWord)) {
          results.push(orderline);
        }
      }

      return results;
    },
  },
  methods: {
    fillBatchData() {
      this.projectDescription = this.batchProject.data.description_long;
      this.description = this.batch.data.textPlanned;
    },
    handleCheckboxChange(event) {
      if (!event.target.checked) {
        this.checkedOrderlines = this.checkedOrderlines.filter(orderline => orderline !== event.target.value);
      }
    },
    isEmpty(obj) {
      for(var prop in obj) {
        if(Object.prototype.hasOwnProperty.call(obj, prop)) {
          return false;
        }
      }

      return JSON.stringify(obj) === JSON.stringify({});
    },
    changeListOrder() {
      if (!this.searchOrderline) {
        // From Old -> New
        this.orderlines.sort((a, b) => {
          return a.loadedDate.localeCompare(b.loadedDate)
        });
      } else if (this.searchOrderline) {
        // From New -> Old
        this.orderlines.sort((a, b) => {
          return b.loadedDate.localeCompare(a.loadedDate)
        });
      }

      this.searchOrderline = !this.searchOrderline;
    },
    backToView() {
      this.$router.push({ path: '/batches' });
    },
    async editBatch() {
      let batch;

      if (
      this.description !== "" &&
      this.checkedOrderlines.length > 0)
      {
        try {
          batch = await this.BatchApi.updateBatch(
              this.id,
              this.description,
              this.checkedOrderlines.length,
              this.checkedOrderlines
          );

          this.$toast.open({
            type: "success",
            message: "Batch " + this.id + " updated",
            duration: 5000,
            dismissible: true,
            position: "top-right",
          });
          this.backToView();
        } catch {
          this.$toast.open({
            type: "error",
            message: "Something went wrong",
            duration: 5000,
            dismissible: true,
            position: "top-right",
          });
        }
      } else {
        this.$toast.open({
          type: "error",
          message: "Please fill in all fields",
          duration: 5000,
          dismissible: true,
          position: "top-right",
        });
      }
    }
  }
}
</script>

<style scoped>

</style>