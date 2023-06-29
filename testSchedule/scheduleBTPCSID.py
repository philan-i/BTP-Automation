import subprocess
import time
import schedule

xml_file_path = r'S:\AB016PN\btpAutomation\testng.xml'
ProjectPath = r'S:\AB016PN\btpAutomation\lib*'
ClassPath = r'S:\AB016PN\btpAutomation\src\test\java\za\co\absa\testcase\LaunchBTP.java'

idle_threshold = 20


# command = f'{xml_file_path}'

a = subprocess.run(["java", r"S:\AB016PN\btp\btpAutomation\src\test\java\za.co.absa.runcsid.TestNGService.java"])


