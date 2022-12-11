<template>
  <div>
    <div class="focus:outline-none focus:ring-2 focus:ring-offset-2 md-flex-col flex-row
            focus:ring-candyPink dark:border-gray-700 bg-white font-normal w-full md-h-12 h-20 flex
            sm-text-sm text-xs border-gray-300 rounded border shadow font-inter justify-between text-left">
      <div class="w-1/3 flex items-center h-20 md-h-10 pl-4 overflow-hidden">
        <p class="text-yInMnBlue font-bold pr-4 "><span class="text-candyPink">Project:</span>
          {{ product.project.description }}</p>
        <div class="absolute inline-flex mt-12 bg-candyPink rounded-md p-1">
          <p class="text-white font-bold pr-2">Custom note</p>
          <Checkbox :id="'custom_note' +product.id" name="custom_note" @update="customNote =$event"
                    :inputData="customNote" placeholder="Custom note"/>
        </div>
      </div>
      <div class="items-center flex">
        <div class="divider"/>
      </div>
      <div class="w-1/3 flex items-center justify-center md-flex-row flex-col">
        <div class="flex-row flex items-center">
          <p class="text-candyPink font-bold">€{{ product.price }}</p>
          <p class="text-yInMrBlue font-bold p-1">/</p>
        </div>
        <p class="text-yInMnBlue font-bold">{{ product.type.product_type_name }}</p>
      </div>
      <div class="items-center flex">
        <div class="divider"/>
      </div>
      <div class="w-1/3 flex items-center">
        <div class="md-flex-row flex-col">
          <label for="amountToOrder" class="font-inter text-yInMnBlue font-bold p-4 flex">Amount</label>
          <InputComponentNumeric :inputData="this.OrderLine.amount" @update="this.OrderLine.amount = $event" id="amount" placeholder="Amount"
                                 :min="1"
                                 required autocomplete="" name="amount" type="number" @keyup="emitUpdate"
                                 :class="'rounded-full border font-inter text-yInMnBlue font-bold md-w-1/2 w-full h-8 place-self-center'"
                                 ref="amountToOrder"/>

        </div>
        <CloseIcon
            class="ml-2 mr-2 bg-candyPink rounded-md w-6 hover:bg-yInMnBlue hover:fill-white"
            @click="this.$emit('deleteProduct', this.index)"
        />
      </div>

    </div>
    <div v-if="customNote" class="focus:outline-none focus:ring-2 focus:ring-offset-2 md-flex-col flex-row
            focus:ring-candyPink dark:border-gray-700 bg-white font-normal w-full min-h-20 flex
            sm-text-sm text-xs border-gray-300 rounded border shadow font-inter justify-between text-left">
      <ckeditor class="w-full mt-1 rounded-md lg:prose-xl"
                :editor="editor" v-model="this.OrderLine.notes" :config="editorConfig" tag-name="textarea"></ckeditor>
    </div>
  </div>
</template>

<script>
import InputComponentNumeric from "@/Components/Form/InputComponentNumeric";
import Product from "@/Models/Product";
import CloseIcon from "@/Components/SvgIcons/CloseIcon.vue";
import Checkbox from "@/Components/Form/Input/ToggleCheckbox.vue";
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import NumberInputWithButtons from "@/Components/Form/Input/NumberInputWithButtons.vue";
import OrderLine from "@/Models/OrderLine";

export default {
  name: "OrderSubItem",
  components: {Checkbox, CloseIcon, InputComponentNumeric},
  props: {
    product: {
      type: Product,
      required: true,
    },
    index: {
      type: Number,
      default: 0,
      required: true,
    }
  },
  created() {
    this.OrderLine = new OrderLine();
  },
  computed:{
    amount(){
      return this.OrderLine.amount;
    }
  },
  data() {
    return {
      lineTotal: 0,
      customNote: false,
      description: '',
      OrderLine: OrderLine,
      editor: ClassicEditor,
      editorConfig: {
        toolbar: ['heading', '|', 'bold', 'italic', 'link', 'bulletedList', 'numberedList', 'blockQuote', 'undo', 'redo']
      }
    }
  },
  mounted() {
    this.emitUpdate();
  },
  methods: {
    emitUpdate() {
      this.lineTotal = Math.round(this.OrderLine.amount * this.product.price * 100) / 100;
      this.OrderLine.transaction_line_total = this.lineTotal;
      this.OrderLine.product_key = this.product.id;

      this.$emit('update',
          {
            index: this.index,
            OrderLine: this.OrderLine,
          });
    }
  },
  watch: {
    OrderLine: {
      handler: function (val, oldVal) {
        this.emitUpdate();
      },
      deep: true
    },
    amount: function () {
      if (!Number(this.OrderLine.amount)) {
        this.OrderLine.amount =0;
      }
      this.emitUpdate();
    }
  },
  emits: ['update', 'deleteProduct'],
}
</script>

<style scoped>
.divider {
  width: 1px;
  height: 60%;
  background-color: #E2E8F0;
}
</style>