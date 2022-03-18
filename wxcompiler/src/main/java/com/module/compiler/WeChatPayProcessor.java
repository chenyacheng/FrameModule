package com.module.compiler;

import com.google.auto.service.AutoService;
import com.module.annotaion.WeChatAppId;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * 微信支付
 * 注解处理器
 *
 * @author BD
 * @date 2021/12/17
 */
@AutoService(Processor.class)
public class WeChatPayProcessor extends AbstractProcessor {

    private Filer filer;
    private Elements elements;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> ret = new HashSet<>();
        ret.add(WeChatAppId.class.getCanonicalName());
        return ret;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elements = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            return false;
        }
        // 指定路径下的微信支付返回页面
        TypeElement payActivity = elements.getTypeElement("com.module.common.ui.WeChatPayActivity");
        String applicationId = null;
        Set<? extends Element> payAppIds = roundEnv.getElementsAnnotatedWith(WeChatAppId.class);
        if (payAppIds != null && !payAppIds.isEmpty()) {
            Element modules = payAppIds.iterator().next();
            applicationId = modules.getAnnotation(WeChatAppId.class).value();
        }
        if (applicationId == null || applicationId.length() == 0) {
            return false;
        }
        TypeSpec payEntryActivity = TypeSpec.classBuilder("WXPayEntryActivity")
                .addModifiers(Modifier.PUBLIC)
                .superclass(ClassName.get(payActivity))
                .build();
        try {
            JavaFile.builder(applicationId + ".wxapi", payEntryActivity)
                    .build()
                    .writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}