<template>
    <div class="embed-responsive embed-responsive-16by9">
        <iframe
            title="Features UI"
            id="features_frame" class="border-0" height="700"
             src="management/toggles-console/index" loading="lazy"
        ></iframe>
        <input type="button" v-on:click="handleLoad()" v-text="$t('feature.home.createLabel')" class="btn btn-primary"/>
    </div>
</template>

<script lang="ts" src="./feature.component.ts">
</script>
