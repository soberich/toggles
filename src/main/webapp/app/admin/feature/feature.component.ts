import axios from 'axios';
import { Component, Vue, Inject } from 'vue-property-decorator';
import AccountService from '@/account/account.service';

@Component
export default class Feature extends Vue {
  @Inject('accountService') private accountService: () => AccountService;
  private hasAnyAuthorityValue = false;

  public get jwt(): string {
    return localStorage.getItem('sc-authenticationToken') || sessionStorage.getItem('sc-authenticationToken');
  }

  public handleLoad(): void {
    axios.get('api/v1/features/create').then(res => {
      // @ts-ignore
      //args.target.href.reload()
    });
  }

  public hasAnyAuthority(authorities: any): boolean {
    this.accountService()
      .hasAnyAuthorityAndCheckAuth(authorities)
      .then(value => {
        this.hasAnyAuthorityValue = value;
      });
    return this.hasAnyAuthorityValue;
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }
}
