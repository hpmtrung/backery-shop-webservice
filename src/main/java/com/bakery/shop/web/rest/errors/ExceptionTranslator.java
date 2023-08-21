package com.bakery.shop.web.rest.errors;

import com.bakery.shop.service.exceptions.FileNotFoundException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.zalando.problem.*;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import org.zalando.problem.violations.ConstraintViolationProblem;
import tech.jhipster.config.JHipsterConstants;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures. The
 * error response follows RFC7807 - Problem Details for HTTP APIs
 * (https://tools.ietf.org/html/rfc7807).
 */
@ControllerAdvice
public class ExceptionTranslator implements ProblemHandling, SecurityAdviceTrait {

    private static final String OBJECT_NAME_KEY = "objectName";
    private static final String FIELD_ERRORS_KEY = "fieldErrors";
    private static final String MESSAGE_KEY = "message";
    private static final String PATH_KEY = "path";
    private static final String VIOLATIONS_KEY = "violations";
    private final Environment env;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public ExceptionTranslator(Environment env) {
        this.env = env;
    }

    /** Post-process the Problem payload to add the message key for the front-end if needed. */
    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {
        if (entity == null) {
            return null;
        }
        Problem problem = entity.getBody();
        if (!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)) {
            return entity;
        }

        HttpServletRequest nativeRequest = request.getNativeRequest(HttpServletRequest.class);
        String requestUri = nativeRequest != null ? nativeRequest.getRequestURI() : StringUtils.EMPTY;
        ProblemBuilder builder = Problem
            .builder()
            .withType(Problem.DEFAULT_TYPE.equals(problem.getType()) ? ErrorConstants.DEFAULT_TYPE : problem.getType())
            .withStatus(problem.getStatus())
            .withTitle(problem.getTitle())
            .with(PATH_KEY, requestUri);

        if (problem instanceof ConstraintViolationProblem) {
            builder
                .with(VIOLATIONS_KEY, ((ConstraintViolationProblem) problem).getViolations())
                .with(MESSAGE_KEY, ErrorConstants.ERR_VALIDATION);
        } else {
            builder.withCause(((DefaultProblem) problem).getCause()).withDetail(problem.getDetail()).withInstance(problem.getInstance());
            problem.getParameters().forEach(builder::with);

            // Use pattern "error.http.[status code]" for error has an empty message
            if (!problem.getParameters().containsKey(MESSAGE_KEY) && problem.getStatus() != null) {
                builder.with(MESSAGE_KEY, "error.http." + problem.getStatus().getStatusCode());
            }
        }
        return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
    }

    @Override
    public ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @Nonnull NativeWebRequest request) {
        // Convert binding result to field error view model
        BindingResult result = ex.getBindingResult();
        List<FieldErrorVM> fieldErrors = result
            .getFieldErrors()
            .stream()
            .map(f -> new FieldErrorVM(f.getField(), f.getCode()))
            .collect(Collectors.toList());
        String objectName = result.getFieldErrors().get(0).getObjectName().replaceFirst("(VM)|(DTO)$", "");
        Problem problem = Problem
            .builder()
            .withType(ErrorConstants.CONSTRAINT_VIOLATION_TYPE)
            .withTitle("Method argument is not valid")
            .withStatus(Status.BAD_REQUEST)
            .with(MESSAGE_KEY, ErrorConstants.ERR_VALIDATION)
            .with(OBJECT_NAME_KEY, objectName)
            .with(FIELD_ERRORS_KEY, fieldErrors)
            .build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleBadRequestException(BadRequestException ex, NativeWebRequest request) {
        Problem problem = Problem.builder().withStatus(Status.BAD_REQUEST).with(MESSAGE_KEY, ex.getMessage()).build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleErrorEntityValidationException(ErrorEntityValidationException ex, NativeWebRequest request) {
        return create(ex, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleEntityNotFoundException(EntityNotFoundException ex, NativeWebRequest request) {
        return create(ex, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleConcurrencyFailure(ConcurrencyFailureException ex, NativeWebRequest request) {
        Problem problem = Problem.builder().withStatus(Status.CONFLICT).with(MESSAGE_KEY, ErrorConstants.ERR_CONCURRENCY_FAILURE).build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleUploadExceedSizeLimitException(MaxUploadSizeExceededException ex, NativeWebRequest request) {
        Problem problem = Problem
            .builder()
            .withStatus(Status.EXPECTATION_FAILED)
            .with(MESSAGE_KEY, ErrorConstants.ERR_UPLOAD_FAILURE)
            .withDetail("File upload exceeds size limitation")
            .build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleFileNotFoundException(FileNotFoundException ex, NativeWebRequest request) {
        Problem problem = Problem
            .builder()
            .withStatus(Status.NOT_FOUND)
            .with(MESSAGE_KEY, ErrorConstants.ERR_FILE_NOT_FOUND)
            .withDetail(ex.getMessage())
            .build();
        return create(ex, problem, request);
    }

    @Override
    public ProblemBuilder prepare(final Throwable throwable, final StatusType status, final URI type) {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());

        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
            if (throwable instanceof HttpMessageConversionException) {
                return Problem
                    .builder()
                    .withType(type)
                    .withTitle(status.getReasonPhrase())
                    .withStatus(status)
                    .withDetail("Unable to convert http message")
                    .withCause(
                        Optional.ofNullable(throwable.getCause()).filter(cause -> isCausalChainsEnabled()).map(this::toProblem).orElse(null)
                    );
            }
            if (throwable instanceof DataAccessException) {
                return Problem
                    .builder()
                    .withType(type)
                    .withTitle(status.getReasonPhrase())
                    .withStatus(status)
                    .withDetail("Failure during data access")
                    .withCause(
                        Optional.ofNullable(throwable.getCause()).filter(cause -> isCausalChainsEnabled()).map(this::toProblem).orElse(null)
                    );
            }
            if (containsPackageName(throwable.getMessage())) {
                return Problem
                    .builder()
                    .withType(type)
                    .withTitle(status.getReasonPhrase())
                    .withStatus(status)
                    .withDetail("Unexpected runtime exception")
                    .withCause(
                        Optional.ofNullable(throwable.getCause()).filter(cause -> isCausalChainsEnabled()).map(this::toProblem).orElse(null)
                    );
            }
        }

        return Problem
            .builder()
            .withType(type)
            .withTitle(status.getReasonPhrase())
            .withStatus(status)
            .withDetail(throwable.getMessage())
            .withCause(
                Optional.ofNullable(throwable.getCause()).filter(cause -> isCausalChainsEnabled()).map(this::toProblem).orElse(null)
            );
    }

    private boolean containsPackageName(String message) {
        // This list is for sure not complete
        return StringUtils.containsAny(message, "org.", "java.", "net.", "javax.", "com.", "io.", "de.", "com.bakery.shop");
    }
}
