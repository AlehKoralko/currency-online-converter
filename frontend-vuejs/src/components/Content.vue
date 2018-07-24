<template>
  <div class="">
    <md-card class="md-layout-item md-size-50 md-small-size-100 my-card">
      <md-card-header>
        <div class="md-title">{{welcomeMessage}}</div>
      </md-card-header>

      <md-card-content>
        <div class="md-layout md-gutter">
          <div class="md-layout-item md-small-size-100">
            <md-field>
              <label for="currentAmount">Current amount</label>
              <md-input type="number" name="currentAmount" id="currentAmount" v-model="currentAmount"/>
            </md-field>
          </div>

          <div class="md-layout-item md-small-size-100">
            <md-field>
              <label>From</label>
              <md-select name="fromAbbr" id="fromAbbr" v-model="currentCurrency">
                <md-option v-for="currency in currencies" :value="currency.abbreviation">
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
                <md-option v-for="currency in currencies" :value="currency.abbreviation">
                  {{currency.abbreviation}} ({{currency.name}})
                </md-option>
              </md-select>
            </md-field>
          </div>
        </div>
      </md-card-content>
      <md-card-actions>
        <md-button @click="res" class="md-primary">Convert</md-button>
      </md-card-actions>
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
    methods: {
      res: function () {
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
          // this.currentCurrency = this.currencies[this.currencies.length - 1];
          // this.targetCurrency = this.currencies[4];
        })
        .catch(e => {
          this.errors.push(e)
      });
    }
  }


</script>

<style lang="scss" scoped>
  h1 {
    font-weight: normal;
  }
  .content {

  }
  .text-center {
    text-align: center;
  }
  .form {
    min-height: 200px;
    max-width: 450px;
    border: 1px solid darkgrey;
    box-shadow: 0 0 10px rgba(0,0,0,0.5);
    margin: auto;
  }
  .field {
    margin: 0 0 20px 20px;
  }
  small {
    display: block;
  }
  .md-progress-bar {
    position: absolute;
    top: 0;
    right: 0;
    left: 0;
  }
  .my-card {
    margin: auto;
    overflow-x:hidden;
  }
</style>
