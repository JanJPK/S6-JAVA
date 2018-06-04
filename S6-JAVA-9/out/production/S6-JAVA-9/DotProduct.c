#include "DotProduct.h"
#include <jni.h>
#include <stdio.h>

JNIEXPORT jdouble JNICALL Java_DotProduct_multi01(JNIEnv *env, jobject thisObj, jdoubleArray a_in, jdoubleArray b_in)
{
    jdouble *a = (*env)->GetDoubleArrayElements(env, a_in, NULL);
    jdouble *b = (*env)->GetDoubleArrayElements(env, b_in, NULL);
    jsize a_len = (*env)->GetArrayLength(env, a_in);

    jdouble scalar = 0.0;
    int i;
    for (i = 0; i < a_len; i++)
    {
        scalar += a[i] * b[i];
    }

    (*env)->ReleaseDoubleArrayElements(env, a_in, a, 0);
    (*env)->ReleaseDoubleArrayElements(env, b_in, b, 0);

    return scalar;
}

JNIEXPORT jdouble JNICALL Java_DotProduct_multi02(JNIEnv *env, jobject thisObj, jdoubleArray a_in)
{
    jdouble *a = (*env)->GetDoubleArrayElements(env, a_in, NULL);
    jsize a_len = (*env)->GetArrayLength(env, a_in);

    jclass java_class = (*env)->GetObjectClass(env, thisObj);
    jmethodID getB_id = (*env)->GetMethodID(env, java_class, "getB", "()[D");
    jdoubleArray b_in = (*env)->CallObjectMethod(env, thisObj, getB_id);
    jdouble *b = (*env)->GetDoubleArrayElements(env, b_in, NULL);

    jdouble scalar = 0.0;
    int i;
    for (i = 0; i < a_len; i++)
    {
        scalar += a[i] * b[i];
    }

    (*env)->ReleaseDoubleArrayElements(env, a_in, a, 0);
    (*env)->ReleaseDoubleArrayElements(env, b_in, b, 0);

    return scalar;
}

JNIEXPORT void JNICALL Java_DotProduct_multi03(JNIEnv *env, jobject thisObj)
{
    jclass java_class = (*env)->GetObjectClass(env, thisObj);
    jmethodID setA_id = (*env)->GetMethodID(env, java_class, "setA", "([D)V");
    jmethodID setB_id = (*env)->GetMethodID(env, java_class, "setB", "([D)V");
    jmethodID multi04_id = (*env)->GetMethodID(env, java_class, "multi04", "()V");

    double a1, a2, a3;
    double b1, b2, b3;
    printf("Podaj {a1, a2, a3}");
    scanf("%lf %lf %lf", &a1, &a2, &a3);
    printf("Podaj {b1, b2, b3}");
    scanf("%lf %lf %lf", &b1, &b2, &b3);
    jdouble a[] = {a1, a2, a3};
    jdouble b[] = {b1, b2, b3};


    jdoubleArray a_out = (*env)->NewDoubleArray(env, 3);
    jdoubleArray b_out = (*env)->NewDoubleArray(env, 3);

    (*env)->SetDoubleArrayRegion(env, a_out, 0, 3, a);
    (*env)->SetDoubleArrayRegion(env, b_out, 0, 3, b);

    (*env)->CallVoidMethod(env, thisObj, setA_id, a_out);
    (*env)->CallVoidMethod(env, thisObj, setB_id, b_out);
    
    (*env)->CallVoidMethod(env, thisObj, multi04_id);    
}
