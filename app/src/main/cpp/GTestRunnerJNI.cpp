#include <jni.h>
#include <string>

#include "GTestRunner.hpp"

extern "C" {
JNIEXPORT jlong JNICALL
Java_org_paleozogt_gtestrunner_GTestRunner_create(JNIEnv *env, jobject obj, jstring jtempDir) {
    const char *ctempDir= env->GetStringUTFChars(jtempDir, 0);
        std::string tempDir= ctempDir;
        env->ReleaseStringUTFChars(jtempDir, ctempDir);

        return (jlong)new GTestRunner(tempDir);
    }

    JNIEXPORT void JNICALL Java_org_paleozogt_gtestrunner_GTestRunner_destroy(JNIEnv *env, jobject obj, jlong cptr) {
        delete (reinterpret_cast<GTestRunner*>(cptr));
    }

    JNIEXPORT jboolean JNICALL Java_org_paleozogt_gtestrunner_GTestRunner_run(JNIEnv *env, jobject obj, jlong cptr, jstring jgtestArg, jobject joutput) {
        GTestRunner *gtestRunner= reinterpret_cast<GTestRunner*>(cptr);

        const char *cgtestArg= env->GetStringUTFChars(jgtestArg, 0);
        std::string gtestArg= cgtestArg;
        env->ReleaseStringUTFChars(jgtestArg, cgtestArg);

        std::string output;
        jboolean passed= gtestRunner->run(gtestArg, output);

        jclass clazz = env->GetObjectClass(joutput);
        jmethodID methodId= env->GetMethodID(clazz, "setValue", "(Ljava/lang/Object;)V");
        env->CallVoidMethod (joutput, methodId, env->NewStringUTF(output.c_str()));

        return passed;
    }

    JNIEXPORT jboolean JNICALL Java_org_paleozogt_gtestrunner_GTestRunner_getListOfTests(JNIEnv *env, jobject obj, jlong cptr, jobject joutput) {
        GTestRunner *gtestRunner= reinterpret_cast<GTestRunner*>(cptr);

        std::string output;
        jboolean passed= gtestRunner->getList(output);

        jclass clazz = env->GetObjectClass(joutput);
        jmethodID methodId= env->GetMethodID(clazz, "setValue", "(Ljava/lang/Object;)V");
        env->CallVoidMethod (joutput, methodId, env->NewStringUTF(output.c_str()));

        return passed;
    }

    JNIEXPORT jboolean JNICALL Java_org_paleozogt_gtestrunner_GTestRunner_runOne(JNIEnv *env, jobject obj, jlong cptr, jstring jtest_name, jobject joutput) {
        GTestRunner *gtestRunner= reinterpret_cast<GTestRunner*>(cptr);

        const char *cgtestName= env->GetStringUTFChars(jtest_name, 0);
        std::string gtestName= cgtestName;
        env->ReleaseStringUTFChars(jtest_name, cgtestName);

        std::string output;
        jboolean passed= gtestRunner->runOne(gtestName, output);

        jclass clazz = env->GetObjectClass(joutput);
        jmethodID methodId= env->GetMethodID(clazz, "setValue", "(Ljava/lang/Object;)V");
        env->CallVoidMethod (joutput, methodId, env->NewStringUTF(output.c_str()));

        return passed;
    }
}

