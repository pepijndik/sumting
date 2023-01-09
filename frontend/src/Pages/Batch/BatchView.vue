<template>
<!--  <div class="flex flex-col sm:flex-row sm:justify-between xl:justify-start">-->
<!--    <div>-->
<!--      <p class="font-inter text-yInMnBlue mb-1">Project</p>-->
<!--      <SearchableDropdown-->
<!--          placeholder="Choose a project"-->
<!--          :fields="['description', 'type.description']"-->
<!--          :text="['description']"-->
<!--          :primarykey="'id'"-->
<!--          :return="'object'"-->
<!--          :options="projects"-->
<!--          @selected="selectedProject = $event"-->
<!--      >-->
<!--      </SearchableDropdown>-->
<!--    </div>-->
<!--  </div>-->
  <div class="h-80 mt-4 text-sm border-gray-300 rounded border shadow overflow-y-scroll overflow-x-hidden
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
            placeholder="Search for your batch"
        />
      </div>
      <div @click="changeBatchList()" class="flex px-2 font-inter text-yInMnBlue border border-gray-300 rounded
      cursor-pointer">
        <p v-if="searchBatch" class="flex m-auto">
          <span>Old</span>
          <svg
              class="fill-yInMnBlue rotate-90 m-auto"
              width="17"
              height="17"
              viewBox="0 0 36 36"
              version="1.1"
              preserveAspectRatio="xMidYMid meet"
              xmlns="http://www.w3.org/2000/svg"
              xmlns:xlink="http://www.w3.org/1999/xlink"
          >
            <path
                d="M27.66,15.61,18,6,8.34,15.61A1,1,0,1,0,9.75,17L17,9.81V28.94a1,1,0,1,0,2,0V9.81L26.25,17a1,1,0,0,0,1.41-1.42Z"
                class="clr-i-outline clr-i-outline-path-1"
            ></path>
          </svg>
          <span>New</span>
        </p>
        <p v-if="!searchBatch" class="flex m-auto">
          <span>Old</span>
          <svg
              class="fill-yInMnBlue rotate-270 m-auto"
              width="17"
              height="17"
              viewBox="0 0 36 36"
              version="1.1"
              preserveAspectRatio="xMidYMid meet"
              xmlns="http://www.w3.org/2000/svg"
              xmlns:xlink="http://www.w3.org/1999/xlink"
          >
            <path
                d="M27.66,15.61,18,6,8.34,15.61A1,1,0,1,0,9.75,17L17,9.81V28.94a1,1,0,1,0,2,0V9.81L26.25,17a1,1,0,0,0,1.41-1.42Z"
                class="clr-i-outline clr-i-outline-path-1"
            ></path>
          </svg>
          <span>New</span>
        </p>
      </div>
    </div>
    <div class="px-3 w-full h-15 lg:h-10 items-center text-sm snap-y snap-mandatory"
         v-for="item in computedObj"
         :key="item.id">
      <BatchRow :batch="item" @deleteBatchEvent="deleteBatch"/>
    </div>
  </div>
</template>

<script>
import SearchableDropdown from "@/Components/Form/SearchableDropdown";
import BatchRow from "@/Components/Batch/BatchRow";

export default {
  name: "BatchView",
  components: {BatchRow},
  inject: ["BatchApi"],
  data() {
    return {
      batches: [],
      // projects: [],
      // selectedProject: null,
      searchKeyWord: '',
      searchBatch: true
    }
  },
  async created() {
    // this.projects = await this.ProjectApi.SearchableDropDown();
    this.batches = await this.BatchApi.getAllBatches();

    this.batches.sort((a, b) => {
      return this.compareDate(a,b);
    });
  },
  computed: {
    computedObj() {
      const results = [];
      const regKeyWord = new RegExp(this.searchKeyWord, 'ig');

      if (this.searchKeyWord === "") {
        return this.batches;
      }

      for (const batch of this.batches) {
        const string = batch.id.toString();
        if (string.match(regKeyWord)) {
          results.push(batch);
        }
      }

      return results;
    },
  },
  methods: {
    compareDate(a,b){
      let aDate = a.updateAt == null ? a.createdAt : a.updateAt;
      let bDate = b.updateAt == null ? b.createdAt : b.updateAt;

      if(aDate != null && bDate != null){
        return aDate.localeCompare(bDate);
      }
      return 0;
    },
    changeBatchList() {
      if (!this.searchBatch) {
        // From Old -> New
        this.batches.sort((a, b) => {
          return this.compareDate(a,b);
        });
      } else if (this.searchBatch) {
        // From New -> Old
        this.batches.sort((a, b) => {
          return this.compareDate(b,a);
        });
      }

      this.searchBatch = !this.searchBatch;
    },
    deleteBatch(id) {
      this.BatchApi.delete(id);
      console.log("Deleted: " + id);
    }
  }
}
</script>

<style scoped>

</style>