// import { Component, Vue } from 'vue-property-decorator';
//
// @Component
// export default class Feature extends Vue {
//   public getUrl(): string {
//     const authToken = localStorage.getItem('sc-authenticationToken') ||
//       sessionStorage.getItem('sc-authenticationToken');
//
//
//
//
//     const url = ''
//     if (authToken) {
//
//       req.headers['Authorization'] = 'Bearer ' + authToken;
//     }
//
//     this.$store.getters.account?.login || '';
//   }
// }
//
//
// //import axios, { AxiosInstance } from 'axios';
// //
// // export default class ActivateService {
// //   private axios: AxiosInstance;
// //
// //   constructor() {
// //     this.axios = axios;
// //   }
// //
// //   public activateAccount(key: string): Promise<any> {
// //     return this.axios.get(`api/activate?key=${key}`);
// //   }
// // }
//
//
//
// //@Component
// // export default class LogsService extends Vue {
// //   public changeLevel(name: string, configuredLevel: string): AxiosPromise<any> {
// //     return axios.post('management/loggers/' + name, { configuredLevel });
// //   }
// //
// //   public findAll(): AxiosPromise<any> {
// //     return axios.get('management/loggers');
// //   }
// // }
//
//
//
// //  public loadConfiguration(): Promise<any> {
// //     return new Promise(resolve => {
// //       axios.get('management/configprops').then(res => {
// //         const properties = [];
// //         const propertiesObject = this.getConfigPropertiesObjects(res.data);
// //         for (const key in propertiesObject) {
// //           if (propertiesObject.hasOwnProperty(key)) {
// //             properties.push(propertiesObject[key]);
// //           }
// //         }
// //
// //         properties.sort((propertyA, propertyB) => {
// //           return propertyA.prefix === propertyB.prefix ? 0 : propertyA.prefix < propertyB.prefix ? -1 : 1;
// //         });
// //         resolve(properties);
// //       });
// //     });
// //   }
// //
// //   public loadEnvConfiguration(): Promise<any> {
// //     return new Promise(resolve => {
// //       axios.get('management/env').then(res => {
// //         const properties = {};
// //         const propertySources = res.data['propertySources'];
// //
// //         for (const propertyObject of propertySources) {
// //           const name = propertyObject['name'];
// //           const detailProperties = propertyObject['properties'];
// //           const vals = [];
// //           for (const keyDetail in detailProperties) {
// //             if (detailProperties.hasOwnProperty(keyDetail)) {
// //               vals.push({ key: keyDetail, val: detailProperties[keyDetail]['value'] });
// //             }
// //           }
// //           properties[name] = vals;
// //         }
// //         resolve(properties);
// //       });
// //     });
// //   }
// //
// //   private getConfigPropertiesObjects(res): any {
// //     // This code is for Spring Boot 2
// //     if (res['contexts'] !== undefined) {
// //       for (const key in res['contexts']) {
// //         // If the key is not bootstrap, it will be the ApplicationContext Id
// //         // For default app, it is baseName
// //         // For microservice, it is baseName-1
// //         if (!key.startsWith('bootstrap')) {
// //           return res['contexts'][key]['beans'];
// //         }
// //       }
// //     }
// //     // by default, use the default ApplicationContext Id
// //     return res['contexts']['features']['beans'];
// //   }
