import { Authority } from '@/shared/security/authority';

const ScUserManagementComponent = () => import('@/admin/user-management/user-management.vue');
const ScUserManagementViewComponent = () => import('@/admin/user-management/user-management-view.vue');
const ScUserManagementEditComponent = () => import('@/admin/user-management/user-management-edit.vue');
const ScConfigurationComponent = () => import('@/admin/configuration/configuration.vue');
const ScDocsComponent = () => import('@/admin/docs/docs.vue');
const ScHealthComponent = () => import('@/admin/health/health.vue');
const ScLogsComponent = () => import('@/admin/logs/logs.vue');
const ScAuditsComponent = () => import('@/admin/audits/audits.vue');
const ScMetricsComponent = () => import('@/admin/metrics/metrics.vue');
const Feature = () => import('@/admin/feature/feature.vue');

export default [
  {
    path: '/feature',
    name: 'Feature',
    component: Feature,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/feature/new',
    name: 'FeatureCreate',
    component: Feature,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/feature/:featureId/edit',
    name: 'FeatureEdit',
    component: Feature,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/feature/:featureId/view',
    name: 'FeatureView',
    component: Feature,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/admin/user-management',
    name: 'ScUser',
    component: ScUserManagementComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/user-management/new',
    name: 'ScUserCreate',
    component: ScUserManagementEditComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/user-management/:userId/edit',
    name: 'ScUserEdit',
    component: ScUserManagementEditComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/user-management/:userId/view',
    name: 'ScUserView',
    component: ScUserManagementViewComponent,
    meta: { authorities: [Authority.ADMIN] },
  },

  {
    path: '/admin/docs',
    name: 'ScDocsComponent',
    component: ScDocsComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/audits',
    name: 'ScAuditsComponent',
    component: ScAuditsComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/sc-health',
    name: 'ScHealthComponent',
    component: ScHealthComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/logs',
    name: 'ScLogsComponent',
    component: ScLogsComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/sc-metrics',
    name: 'ScMetricsComponent',
    component: ScMetricsComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/sc-configuration',
    name: 'ScConfigurationComponent',
    component: ScConfigurationComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
];
