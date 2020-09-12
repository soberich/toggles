package com.example.sc.config

import java.io.IOException
import java.lang.reflect.Field
import java.nio.charset.StandardCharsets
import java.util.Objects.nonNull
import java.util.stream.Collectors
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.tools.FileObject
import javax.tools.StandardLocation
import org.springframework.boot.configurationprocessor.ConfigurationMetadataAnnotationProcessor
import org.springframework.boot.configurationprocessor.MetadataStore
import org.springframework.boot.configurationprocessor.metadata.ConfigurationMetadata
import org.springframework.boot.configurationprocessor.metadata.ItemMetadata
import org.springframework.util.ReflectionUtils

/**
 * TODO: This is WIP.
 */
@SupportedAnnotationTypes("*")
class TogglesConfigurationMetadataAnnotationProcessor : ConfigurationMetadataAnnotationProcessor() {
    @Synchronized
    override fun init(env: ProcessingEnvironment?) {
        super.init(env)
        val metadataStoreField: Field = ReflectionUtils.findField(ConfigurationMetadataAnnotationProcessor::class.java, "metadataStore")
        ReflectionUtils.makeAccessible(metadataStoreField)
        ReflectionUtils.setField(metadataStoreField, this, MyMetadataStore(env!!))
    }

    private class MyMetadataStore(private val environment: ProcessingEnvironment) : MetadataStore(environment) {
        @Throws(IOException::class)
        override fun writeMetadata(metadata: ConfigurationMetadata) {
            if (metadata.items.isNotEmpty()) {
                createMetadataPropertiesResource().openOutputStream().use { outputStream -> outputStream.write(getProps(metadata).toByteArray(StandardCharsets.UTF_8)) }
            }
        }

        private fun getProps(metadata: ConfigurationMetadata): String {
            return metadata.items.stream()
                .filter { item -> item.isOfItemType(ItemMetadata.ItemType.PROPERTY) }
                .map { item -> if (nonNull(item.defaultValue)) item.name.toString() + "=" + item.defaultValue else "# " + item.name.toString() + "=" }
                .collect(Collectors.joining("\n"))
        }

        @Throws(IOException::class)
        private fun createMetadataPropertiesResource(): FileObject {
            return environment.filer.createResource(StandardLocation.CLASS_OUTPUT, "", Companion.METADATA_PROPERTIES_PATH)
        }

        companion object {
            const val METADATA_PROPERTIES_PATH = "META-INF/spring-configuration-metadata.properties"
        }
    }
}
