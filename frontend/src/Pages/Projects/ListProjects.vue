<template>
  <div>
    <div
        class="h-10/12 mt-4 text-sm border-gray-300 rounded border shadow overflow-y-scroll overflow-x-hidden scrollbar-thin scrollbar-thumb-yInMnBlue">
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
              placeholder="Search for your project"
          />
        </div>
        <div class="flex px-2 font-inter text-yInMnBlue
       cursor-pointer">
          <SearchableDropdown
              placeholder="Select a limit"
              :options="maxLimit()"
              :primarykey="'amount'"
              :fields="['amount']"
              :text="['amount']"
              :selected-item="perPage"
              :return="'primarykey'"
              @selected="getProjects($event)"
          >
            <slot name="icon">
              <CloseIcon/>
            </slot>
          </SearchableDropdown>
        </div>
        <div @click="changeListOrder()" class="flex px-2 font-inter text-yInMnBlue border border-gray-300 rounded
       cursor-pointer">
          <p v-if="searchOrder" class="flex m-auto">
            <span>Old</span>
            <svg class="fill-yInMnBlue rotate-90 m-auto" width="17" height="17" viewBox="0 0 36 36" version="1.1"
                 preserveAspectRatio="xMidYMid meet" xmlns="http://www.w3.org/2000/svg"
                 xmlns:xlink="http://www.w3.org/1999/xlink">
              <path
                  d="M27.66,15.61,18,6,8.34,15.61A1,1,0,1,0,9.75,17L17,9.81V28.94a1,1,0,1,0,2,0V9.81L26.25,17a1,1,0,0,0,1.41-1.42Z"
                  class="clr-i-outline clr-i-outline-path-1"></path>
            </svg>
            <span>New</span>
          </p>
          <p v-if="!searchOrder" class="flex m-auto">
            <span>Old</span>
            <svg class="fill-yInMnBlue rotate-270 m-auto" width="17" height="17" viewBox="0 0 36 36" version="1.1"
                 preserveAspectRatio="xMidYMid meet" xmlns="http://www.w3.org/2000/svg"
                 xmlns:xlink="http://www.w3.org/1999/xlink">
              <path
                  d="M27.66,15.61,18,6,8.34,15.61A1,1,0,1,0,9.75,17L17,9.81V28.94a1,1,0,1,0,2,0V9.81L26.25,17a1,1,0,0,0,1.41-1.42Z"
                  class="clr-i-outline clr-i-outline-path-1"></path>
            </svg>
            <span>New</span>
          </p>
        </div>
      </div>
      <div class="mt-7 overflow-x-auto">
        <table class="w-full whitespace-nowrap">
          <tbody
              v-for="project in filter"
              :key="project.id">
          <ProjectRow :project="project" @deleteProjectEvent="deleteProject"/>
          </tbody>
        </table>
      </div>
      <!--    <div class="px-3 w-full h-15 lg:h-10 items-center text-sm snap-y snap-mandatory"-->
      <!--         v-for="project in filter"-->
      <!--         :key="project.id">-->
      <!--    -->
      <!--    </div>-->
    </div>
    <div class="my-12 max-w-lg">
      <Pagination
          :total-pages="totalPages"
          :total="total"
          :per-page="perPage"
          :current-page="currentPage"
          :has-more-pages="hasMorePages"
          @pagechanged="showMore"/>
    </div>
  </div>
</template>

<script>
import ProjectRow from "@/Components/Project/ProjectRow.vue";
import Pagination from "@/Components/Pagination/Pagination.vue";

import SearchableDropdown from "@/Components/Form/SearchableDropdown";
import CloseIcon from "@/Components/SvgIcons/CloseIcon.vue";

export default {
  name: "ListProjects",
  inject: ["ProjectApi"],
  computed: {
    /**
     * Filter projects by search keyword
     * @returns {[]|*[]}
     */
    filter() {
      const results = [];
      const regKeyWord = new RegExp(this.searchKeyWord, 'ig');

      if (this.searchKeyWord === "") {
        return this.projects;
      }

      for (const project of this.projects) {
        const string = project.id.toString();
        if (string.match(regKeyWord)) {
          results.push(project);
        }
      }

      return results;
    },

  },
  /**
   * Gets all project from the database
   * @returns {Promise<void>}
   */
  async created() {
    await this.getProjects()

  },

  data() {
    return {
      limits: [],
      projects: [],
      searchKeyWord: "",
      searchOrder: true,
      totalPages: 0,
      total: 0,
      perPage:10,
      currentPage: 0,
      hasMorePages: false
    };
  },
  components: {
    CloseIcon,
    SearchableDropdown,
    Pagination,
    ProjectRow
  },
  methods: {
    /**
     * Goes to the next page of projects
     * @param page
     * @returns {Promise<void>}
     */
    async showMore(page) {
      this.currentPage = page;
      await this.getProjects()
    },
    /**
     * Changes the project list from old to new and vice versa
     */
    changeListOrder() {
      if (!this.searchOrder) {
        // From Old -> New
        this.projects.sort((a, b) => {
          return a.createdAt.diff(b.createdAt);
        });
      } else if (this.searchOrder) {
        // From New -> Old
        this.projects.sort((a, b) => {
          return b.createdAt.diff(a.createdAt)
        });
      }

      this.searchOrder = !this.searchOrder;
    },
    /**
     * Deletes a project with the given id
     * @param id
     */
    deleteProject(id) {
      this.ProjectApi.delete(id);
      console.log("Deleted: " + id);
    },
    /**
     * Gets all projects from the database with pagination
     * @param limit
     * @returns {Promise<void>}
     */
    async getProjects(limit) {
      if(limit > 0){
        this.perPage = limit;
      }


      const page = await this.ProjectApi.findAll({
        page: this.currentPage,
        size: this.perPage,
        pagination: true,
      });
      this.currentPage = page.currentPage;
      this.totalPages = page.totalPages -1;
      this.total = page.totalItems;
      this.projects = page.data;
    },
    /**
     * @returns {*[]}
     */
    maxLimit() {
      let items =[]
      const lowerNumber = 0;
      const higherNumber = this.total;

      //Get an array of
      for (let i = lowerNumber; i <= higherNumber; i++) {
        let flag = 0;

        for (let j = 2; j < i; j++) {
          if (i % j === 0) {
            flag = 1;
            break;
          }
        }
        // if number greater than 1 and not divisible by other numbers
        if (i > 1 && flag === 0) {
         items.push({amount: i});
        }
      }
      return items;
    },
  }
}
</script>

<style scoped>

</style>