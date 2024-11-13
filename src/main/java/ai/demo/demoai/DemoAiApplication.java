package ai.demo.demoai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootApplication
public class DemoAiApplication {

    @RestController
    public static class DemoAiController {

        private static final String TEMPLATE = """
                
                You're assisting with questions about GoTechWorld 2024 Romania.
                GoTech World is the largest B2B expo-conference for IT & digital solutions in CEE region.
                Attracting over 15,000 business visitors annually, it serves as a platform for discovering the 
                latest tech innovations, networking with industry leaders and learning from top international speakers.
                
                Use the data from the DOCUMENTS section to provide
                accurate answers but act as if you knew this information already.
                If you don't know the answer don't guess, just say that you don't know.
                
                DOCUMENTS:
                {documents}
                
                """;

        private final ChatClient.Builder builder;
        private final VectorStore vectorStore;

        public DemoAiController(ChatClient.Builder builder, VectorStore vectorStore) {
            this.builder = builder;
            this.vectorStore = vectorStore;
        }

        @GetMapping("/ask/{question}")
        public ResponseEntity<String> ask(@PathVariable String question) {

//            var listOfSimilarDocuments = this.vectorStore.similaritySearch(question);
//            var documents = listOfSimilarDocuments
//                    .stream()
//                    .map(Document::getContent)
//                    .collect(Collectors.joining(System.lineSeparator()));
//            var systemMessage = new SystemPromptTemplate(TEMPLATE)
//                    .createMessage(Map.of("documents", documents));
//            var userMessage = new UserMessage(question);
//
//            var prompt = new Prompt(List.of(systemMessage, userMessage));
//            var prompt = new Prompt(List.of(systemMessage, userMessage));

            var prompt = new Prompt(question);

            var chatClient = builder
                    .build();
            var result = chatClient.prompt(prompt).call().content();

            return ResponseEntity.ok(result);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoAiApplication.class, args);
    }

    @Bean
    TokenTextSplitter tokenTextSplitter() {
        return new TokenTextSplitter();
    }

    @Bean
    @ConditionalOnProperty(value = "ai.demo.demoai.init-documents", havingValue = "true")
    ApplicationRunner initFiles(@Value("classpath:${ai.demo.demoai.data-folder}") Resource dataFolderResource,
                                VectorStore vectorStore,
                                TokenTextSplitter tokenTextSplitter) {
        return _ -> {
            var dataFolder = dataFolderResource.getFile();

            if (!dataFolder.exists() || !dataFolder.isDirectory()) {
                throw new IllegalArgumentException(dataFolder.getName() + " does not exist or is not a directory");
            }

            for (var file : Objects.requireNonNull(dataFolder.listFiles())) {
                if (file.isFile()) {
                    var readerConfig = PdfDocumentReaderConfig.builder().build();
                    var pdfReader = new PagePdfDocumentReader(new FileSystemResource(file), readerConfig);
                    vectorStore.accept(tokenTextSplitter.apply(pdfReader.get()));
                }
            }
        };
    }

}
