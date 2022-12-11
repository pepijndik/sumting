<template>
  <div>
    <div class="grid w-full grid-cols-1 lg:grid-cols-3 md:grid-cols-1 gap-1 mt-7">
      <div>
        <p class="font-inter text-yInMnBlue">Client</p>
        <SearchableDropdown
            class="mt-1"
            placeholder="Choose a client"
            :fields="['name', 'email']"
            :text="['name', 'email']"
            :primarykey="'id'"
            :icon="true"
            @selected="selectedClient = $event"
            :options="clients">
          <slot>
            <UserIcon class="w-5 h-5 text-gray-400 group-hover:text-gray-500" aria-hidden="true"/>
          </slot>
        </SearchableDropdown>
      </div>
    <div>
      <p class="font-inter text-yInMnBlue">Currency</p>
      <SearchableDropdown
          class="mt-1"
          placeholder="Choose a currency"
          :fields="['symbol', 'name', 'code']"
          :text="['symbol','code']"
          :primarykey="'name'"
          :icon="false"
          @selected="selectedCurreny = $event"
          :options="currencies">
      </SearchableDropdown>
    </div>
      <div>
        <p class="font-inter text-yInMnBlue">Order type</p>
        <SearchableDropdown
            class="mt-1"
            placeholder="Choose a currency"
            :fields="['id', 'type']"
            :text="['description']"
            :primarykey="'id'"
            :icon="false"
            @selected="orderType = $event"
            :options="orderTypes">
        </SearchableDropdown>
      </div>
    </div>

    <h3 class="font-inter text-2xl text-yInMnBlue font-bold">Order info</h3>
    <p class="font-inter text-yInMnBlue">Description</p>

    <ckeditor class="block w-full mt-1 rounded-md lg:prose-xl"
              :editor="editor" v-model="description" :config="editorConfig" tag-name="textarea"></ckeditor>

    <p class="font-inter text-yInMnBlue mt-2">Project(s)</p>
    <SearchableDropdown
        class="mt-1 pb-4"
        placeholder="Select project(s)"
        :options="projects"
        :fields="['id','description', 'type.description']"
        :primarykey="'id'"
        :text="['description', 'type.description']"
        :return="'object'"
        :cleanAfterSelect="true"
        :max-items="20"
        @selected="searchSelection = $event"/>
    <OrderTotalCostSubItem :products="products" @removeSelected="removeSelected" @updatedTotalCost="totalCost = $event"/>
    <button v-on:click="createOrder" class="my-2 w-full sm:w-80 bg-candyPink transition duration-150 ease-in-out hover:bg-indigo-600 rounded
    text-white font-inter px-8 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-600">
      Create order
    </button>
  </div>
</template>

<script>
import SearchableDropdown from "@/Components/Form/SearchableDropdown";
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import OrderTotalCostSubItem from "@/Components/Form/SubItems/OrderTotalCostSubItem";
import '@ckeditor/ckeditor5-build-classic/build/translations/nl';
import { ref } from '@vue/reactivity';
import UserIcon from "@/Components/SvgIcons/userIcon.vue";
export default {
  name: "OrderCreate",
  components: {
    UserIcon,
    OrderTotalCostSubItem, SearchableDropdown,

  },
  inject: ['ProjectApi', "ProductApi", "UserApi","Curreny"
  ,"OrderApi"],
  data() {
    return {
      editor: ClassicEditor,
      description: ref('<h2>Sumting order description</h2>'),
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
      orderTypes: [],
      searchSelection: null,
      selectedProjects: [],
      selectedClient: null,
      projects: [],
      clients: [],
      currencies:[],
      selectedCurreny: null,
      orderType: null,
      products: [],
      totalCost: 0,
    }
  },
  async created() {
    this.projects = await this.ProjectApi.SearchableDropDown();
    this.clients = await this.UserApi.GetAllUsers();
    this.currencies = this.Curreny.getCurrencyList();
    this.orderTypes = await this.OrderApi.GetOrderTypes()
  },
  watch: {
    async searchSelection(val) {
      //When the dropdown selection changes it adds the value obtained from this event to the list of projects selected.
      if (val !== null) {
        await this.addProjectToSelected(val);
      }
    },
  },
  methods: {
    removeSelected(i) {
      this.searchSelection = null;
      this.selectedProjects.splice(i, 1);
    },
    async addProjectToSelected(project) {
      let found = false;
      for (let i = 0; i < this.selectedProjects.length; i++) {
        if (this.selectedProjects[i].id === project.id) {
          found = true;
          break;
        }
      }
      if (!found) {
        this.selectedProjects.push(project);
        await this.findProductsForProjects(this.selectedProjects);
      }
    },
    async findProductsForProjects(projects) {
      let tempProducts = [];
      for (const project of projects) {
        //Searches for the product corresponding to the project id.
        if (project.id !== undefined) {
          await this.ProductApi.findProductByProjectId(project.id).then((data) => {
            data.forEach((product) => {
              tempProducts.push(product);
            });
          }).catch((error) => {
            throw error;
          });
        }
      }

      this.products = tempProducts;

    },
    async createOrder(){
      //@TODO: Prepare Json for order creation.
      console.log("selected CLient: "+this.selectedClient);
      console.log(this.selectedClient);
      console.log("selected Products: "+this.products);
      console.log(this.products);
      console.log(this.totalCost);
      console.log("Description: "+this.description);
    }
  }
}
</script>

<style scoped>

</style>