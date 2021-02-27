package me.skylands.skypvp

import com.google.common.reflect.ClassPath
import java.util.*
import kotlin.collections.HashSet

@Suppress("UnstableApiUsage")
class PackageClassIndexer {

    companion object {

        fun <T> resolveInstances(pckg: String, superClass: Class<T>): Set<T> {
            val classLoader: ClassLoader = PackageClassIndexer::class.java.classLoader
            val objects: MutableSet<T> = HashSet()
            for (classInfo in ClassPath.from(classLoader).getTopLevelClassesRecursive(pckg)) {
                val clazz = Class.forName(classInfo.name, true, classLoader)
                if (listOf(*clazz.interfaces).contains(superClass) || clazz.superclass == superClass) {
                    objects.add(superClass.cast(clazz.newInstance()))
                }
            }
            return Collections.unmodifiableSet(objects)
        }

    }

}