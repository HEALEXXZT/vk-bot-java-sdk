public interface IHASH {

    String e_md5(String hash);
    String e_base64(String hash);
    String e_sha256(String hash);
    String e_sha256$(String hash);

}
