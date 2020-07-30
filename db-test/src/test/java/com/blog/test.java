private void mockThe3rdPartyService(MockServerClient client) throws IOException {
        client
        .when(
        request()
        .withMethod("POST")
        .withPath("/catpay/pay-request")
        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .withBody(json(readFile(mockCatpayRequest)))
        )
        .respond(
        response()
        .withBody(json(readFile(mockCatpayResponse)))
        );
}
