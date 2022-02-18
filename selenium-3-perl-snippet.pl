use Selenium::Remote::Driver;
#Input capabilities
my $extraCaps = {
  "os_version" => "10.0",
"device" => "Samsung Galaxy S20",
"real_mobile" => "true",
"browserstack.local" => "false",
  'name' => 'perl Android', # test name
  'build' => 'Selenium-3-code-snippet-sanity' # CI/CD job or build name
};
my $login = "";
my $key = "";
my $host = "$login:$key\@hub-cloud.browserstack.com";
my $driver = new Selenium::Remote::Driver('remote_server_addr' => $host,
  'port' => '80', 'extra_capabilities' => $extraCaps);
# Searching for 'BrowserStack' on google.com
$driver->get('https://bstackdemo.com/');
my $title =   $driver->get_title();

  # waiting until the Add to Cart button is displayed on webpage and then clicking it
my $product =   $driver->find_element('//*[@id="1"]/p');
my $product_text = $product->get_text();

 # waiting until the Add to Cart button is displayed on webpage and then clicking it
my $cart_btn =   $driver->find_element('//*[@id="1"]/div[4]');
$cart_btn->click();

my $product_in_cart =   $driver->find_element('//*[@id="__next"]/div/div/div[2]/div[2]/div[2]/div/div[3]/p[1]');
my $product_in_cart_text = $product_in_cart->get_text();

# Setting the status of test as 'passed' or 'failed' based on the condition; if title of the web page starts with 'BrowserStack'
if ($product_in_cart_text eq $product_text) {
	$driver->execute_script('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"passed", "reason": "Yaay! Title matched!"}}');
} else {
	$driver->execute_script('browserstack_executor: {"action": "setSessionStatus", "arguments": {"status":"failed", "reason": "Oops! Title did not match!"}}');
}

$driver->quit; 
