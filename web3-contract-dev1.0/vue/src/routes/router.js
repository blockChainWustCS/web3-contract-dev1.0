import { createRouter, createWebHashHistory } from "vue-router"
const routes = [{
    path: "/",
    components: {
        default: () =>
            import ('../pages/Home.vue')
    }
}]

const router = createRouter({
    history: createWebHashHistory(),
    routes: routes
});

export default router;