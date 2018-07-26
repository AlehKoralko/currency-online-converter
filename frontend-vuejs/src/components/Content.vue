<template>
  <div>
    <md-card class="md-layout-item md-size-50 md-small-size-100 my-card">
      <md-card-header>
        <div class="md-title">{{welcomeMessage}}</div>
      </md-card-header>

      <md-card-content>
        <div class="md-layout md-gutter">
          <div class="md-layout-item md-small-size-100">
            <md-field>
              <label for="currentAmount">Current amount</label>
              <md-input type="number" min="0" name="currentAmount" id="currentAmount" v-model="currentAmount"/>
            </md-field>
          </div>

          <div class="md-layout-item md-small-size-100">
            <md-field>
              <label>From</label>
              <md-select name="fromAbbr" id="fromAbbr" v-model="currentCurrency">
                <md-option v-for="currency in currencies" :value="currency.abbreviation" :key="currency.abbreviation">
                  {{currency.abbreviation}} ({{currency.name}})
                </md-option>
              </md-select>
            </md-field>
          </div>
        </div>

        <div class="md-layout md-gutter">
          <div class="md-layout-item md-small-size-100">
            <md-field>
              <label>Target amount</label>
              <md-input v-model="targetAmount" readonly></md-input>
            </md-field>
          </div>

          <div class="md-layout-item md-small-size-100">
            <md-field>
              <label>To</label>
              <md-select name="toAbbr" id="toAbbr" v-model="targetCurrency">
                <md-option v-for="currency in currencies" :value="currency.abbreviation" :key="currency.abbreviation">
                  {{currency.abbreviation}} ({{currency.name}})
                </md-option>
              </md-select>
            </md-field>
          </div>
        </div>
      </md-card-content>
    </md-card>
  </div>
</template>

<script>
  import axios from 'axios'

  export default {
    name: "Content",
    data() {
      return {
        welcomeMessage: 'Currency online converter',
        currencies: [],
        errors: [],
        currentCurrency: null,
        targetCurrency: null,
        currentAmount: null,
        targetAmount: 0
      }
    },
    watch: {
      currentCurrency: function () {
        if (this.currentCurrency && this.targetCurrency && this.currentAmount) {
          this.convert();
        }
      },
      targetCurrency: function () {
        if (this.currentCurrency && this.targetCurrency && this.currentAmount) {
          this.convert();
        }
      },
      currentAmount: function () {
        if (this.currentCurrency && this.targetCurrency && this.currentAmount) {
          this.convert();
        }
      }
    },
    methods: {
      convert: function () {
        axios.get('/api/convert', {
          params: {
            current: this.currentCurrency,
            target: this.targetCurrency,
            amount: this.currentAmount
          }
        })
          .then(response => {
            this.targetAmount = response.data;
          })
          .catch(e => {
            this.errors.push(e)
        });
      }
    },
    created() {
      axios.get('/api')
        .then(currencies => {
          this.currencies = currencies.data;
        })
        .catch(e => {
          this.errors.push(e)
      });
    }
  }


</script>

<style scoped>
  .my-card {
    margin: auto;
    overflow-x:hidden;
  }
</style>
