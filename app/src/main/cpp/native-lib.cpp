#include <jni.h>
#include <string>
#include <sstream>

std::stringstream ss;
std::string output;
bool increasing;
int increment = 1;
int maxStars;
int terminator;
int counter;
int stopper;

void printStars() {
    ss << "*";
    counter += 1;
    do {
        if (counter < stopper) {
            printStars();
        } else {
            ss << "\n";
            stopper += increment;
            increasing ? counter = 0 : counter = terminator;
        }
    } while (stopper != terminator);
}

extern "C"
JNIEXPORT jstring

JNICALL

Java_central_com_MainActivity_stringFromJNI(JNIEnv *env, jobject /* this */, int myNum, bool myBool) {
    // the contents of the sstream is preserved between executions
    ss.str("");

    maxStars = myNum;
    increasing = myBool;
    if (increasing) {
        counter = 0;
        stopper = 0;
        terminator = maxStars;
        increment = 1;
    } else {
        counter = 0;
        stopper = maxStars;
        terminator = 0;
        increment = -1;
    }

    printStars();
    output = ss.str();
    return env->NewStringUTF(output.c_str());
}