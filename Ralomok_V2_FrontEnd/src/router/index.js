import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      meta: {withoutLogin: true},
      component: () => import('@/views/MainView.vue')
    },
  ]
})

// beforEach는 전역 네비게이션 가드로, 라우터를 통한 모든 이동 전에 호출됩니다.
// to에는 목적지의 정보, from에는 출발지의 정보가 담겨있으며,
// return false; 를 하면 이동이 취소됩니다.  by 종범
router.beforeEach(async (to, from) => {
  window.scrollTo(0, 0);
  return true;
});

export default router
