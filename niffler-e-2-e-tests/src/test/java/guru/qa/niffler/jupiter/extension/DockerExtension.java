//package niffler.jupiter.extension;
//
//import niffler.jupiter.annotation.Container;
//import org.junit.jupiter.api.extension.AfterEachCallback;
//import org.junit.jupiter.api.extension.BeforeEachCallback;
//import org.junit.jupiter.api.extension.ExtensionContext;
//import org.junit.platform.commons.support.AnnotationSupport;
//
//public class DockerExtension implements BeforeEachCallback, AfterEachCallback {
//
//    @Override
//    public void beforeEach(ExtensionContext context) throws Exception {
//        context.getElement().ifPresent(element -> AnnotationSupport.findAnnotation(element, Container.class)
//                .map(annotation -> startContainer(context, annotation.image(), annotation.env(), annotation.ports()))
//                .ifPresent(containerId -> context.getStore(context).put("containerId", containerId))
//        );
//    }
//    @Override
//    public void afterEach(ExtensionContext context) throws Exception {
//        String containerId = getContainerId(context)
//    }
//
//
//}
