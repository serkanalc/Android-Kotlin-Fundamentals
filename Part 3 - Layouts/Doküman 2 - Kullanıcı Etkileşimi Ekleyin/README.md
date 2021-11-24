# Kullanıcı Etkileşimi Ekleyin

- [Metin girişi İçin Bir EditText Ekleyin](#1)
- [EditText'inize Stil Verin](#2)
- [Bir Düğme Ekleyin ve Ona Stil Verin](#3)
<!--
- [Nickname Görüntülemek İçin Bir TextView Ekleyin](#4)
- [DONE Düğmesine Bir Click Listener Ekleyin](#5)
- [TextView Nickname'ine bir Click Listener Ekleyin](#3)
-->

Bu dokümanda, Kullanıcı etkileşimi eklemek için AboutMe uygulamasını genişletirsiniz. Nickname görüntülemek için nickname alanı, bir **DONE** düğmesi ve bir metin görünümü eklersiniz. Kullanıcı bir nickname girip **DONE** düğmesine dokunduğunda, metin görünümü girilen nickname'i gösterecek şekilde güncellenir. Kullanıcı, metin görünümüne dokunarak takma adı tekrar güncelleyebilir.

![image](https://user-images.githubusercontent.com/70329389/143226650-2669c75d-2897-472c-8a9f-93182477c3c7.png)


## <a name="1"></a>Metin girişi İçin Bir EditText Ekleyin

Bu görevde, kullanıcının bir nickname(takma ad) girmesine izin vermek için bir EditText giriş alanı eklersiniz.

### Aşama 1 : Başlangıç

1. Önceki bir Dokümandan AboutMe uygulamasına sahip değilseniz, [AboutMeInteractive-Starter](https://github.com/serkanalc/Android-Kotlin-Fundamentals-Projects/tree/main/AboutMe%20-%20Project) başlangıç kodunu indirin. Bu, önceki Dokümanda bitirdiğiniz kodun aynısı.

2. Android Studio'da AboutMeInteractive-Starter projesini açın.
3. Uygulamayı çalıştırın. Kaydırma görünümünde bir ad metni görünümü, bir yıldız resmi ve uzun bir metin parçası görürsünüz.

![image](https://user-images.githubusercontent.com/70329389/143229037-deab1ec9-2a36-4f51-9bb4-ee845f587a52.png)

Kullanıcı uygulamayla etkileşime girebiliyorsa, örneğin kullanıcı metin girebiliyorsa, uygulamalar daha ilginçtir. Metin girişini kabul etmek için Android, metin düzenleme adı verilen bir kullanıcı arabirimi (UI) widget'ı sağlar. TextView'in bir alt sınıfı olan **EditText'i** kullanarak bir düzenleme metni tanımlarsınız. Düzenleme metni, kullanıcının aşağıdaki ekran görüntüsünde gösterildiği gibi metin girişini girmesine ve değiştirmesine olanak tanır.

![image](https://user-images.githubusercontent.com/70329389/143229579-649fca35-53e3-4cb8-bda3-626a6c81e4ca.png)

### Aşama 1 : Edittext Ekleyin

1. Android Studio'da, Design sekmesinde **aktivite_main.xml** layout dosyasını açın.
2. Palet bölmesinde Metin'e tıklayın.

![image](https://user-images.githubusercontent.com/70329389/143229780-a6ce2fc4-791c-4064-9835-a4bc678be678.png)

Bir TextView olan Ab **TextView**, **Palet** bölmesindeki metin öğeleri listesinin en üstünde gösterilir. Ab **TextView**'in altında birden çok EditText görünümü vardır.

Palet bölmesinde, **TextView** simgesinin Ab harflerini alt çizgi olmadan nasıl gösterdiğine dikkat edin. Ancak **EditText** simgeleri, Ab'nin altı çizili olarak gösterilir. Alt çizgi, görünümün düzenlenebilir olduğunu gösterir.

Android, EditText görünümlerinin her biri için farklı nitelikler ayarlar ve sistem uygun yazılım giriş yöntemini (ekran klavyesi gibi) görüntüler.

3. Bir **PlainText** düzenleme metnini Bileşen Ağacına sürükleyin ve onu **name_text**'in altına ve **star_image**'in üstüne yerleştirin.

![image](https://user-images.githubusercontent.com/70329389/143230316-9ecb54ed-b14b-45ba-bc25-b1e184f263fa.png)

4. EditText görünümünde aşağıdaki nitelikleri ayarlamak için **Attributes** bölmesini kullanın.

| Attribute | Value |
|---|---|
| id | nickname_edit |
| layout_width | match_parent (default) |
| layout_height | wrap_content (default) |

5. Uygulamanızı çalıştırın. Yıldız görüntüsünün üzerinde, varsayılan "Name" metnine sahip bir düzenleme metni görürsünüz.

![image](https://user-images.githubusercontent.com/70329389/143231208-6ad92d7f-4376-4254-ab6a-24008acd0e64.png)

## <a name="2"></a>EditText'inize Stil Verin

Bu aşamada, bir hint ekleyerek, metin hizalamasını değiştirerek, stili **NameStyle** olarak değiştirerek ve giriş türünü ayarlayarak **EditText** görünümünüzü şekillendirirsiniz.

### Aşama 1 : Bid Hint Text Ekleyin

1. **string.xml** dosyasındaki ipucu için yeni bir dize kaynağı ekleyin.

```
<string name="what_is_your_nickname">What is your Nickname?</string>
```

> İpucu: Kullanıcıların düzenlenebilir alanlar için hangi girdinin beklendiğini anlamalarına yardımcı olmak için her EditText görünümünde bir ipucu göstermek iyi bir uygulamadır.

2. Aşağıdaki nitelikleri **EditText** görünümüne ayarlamak için **Attriburtes** bölmesini kullanın:

| Attribute | Value |
|---|---|
| style | @style/NameStyle |
| textAlignment | (center) |
| hint | @string/what_is_your_nickname |

3. **Attributes** bölmesinde, **text** özelliğinden **Name** değerini kaldırın. İpucunun görüntülenmesi için text öznitelik değerinin boş olması gerekir.

### Aşama 2 : inputType Niteliğini Ayarlayın

**inputType** özelliği, kullanıcıların **EditText** görünümüne girebilecekleri giriş türünü belirtir. Android sistemi, ayarlanan giriş tipine bağlı olarak uygun giriş alanını ve ekran klavyesini görüntüler.

Tüm olası giriş türlerini görmek için, **Attributes** bölmesinde **inputType** alanını tıklayın veya alanın yanındaki üç noktayı tıklayın. Kullanabileceğiniz tüm giriş türlerini, o anda etkin olan giriş türü işaretli olarak gösteren bir liste açılır. Birden fazla giriş tipi seçebilirsiniz.

![image](https://user-images.githubusercontent.com/70329389/143233624-2241b002-79df-411d-b810-24e5314f46c9.png)

Örneğin, parolalar için **textPassword** değerini kullanın. Metin alanı, kullanıcının girişini gizler.

![image](https://user-images.githubusercontent.com/70329389/143233678-17665421-0bc4-48f8-8caf-d20884f84a44.png)

Telefon numaraları için **phone** değerini kullanın. Bir sayı tuş takımı görüntülenir ve kullanıcı yalnızca sayıları girebilir.

![image](https://user-images.githubusercontent.com/70329389/143233763-c2694761-ed19-4522-a289-ae54445a2eb4.png)

Takma ad alanı için giriş türünü ayarlayın:

1. **Nick_edit** düzenleme metni için **inputType** özniteliğini **textPersonName** olarak ayarlayın.
2. Component Tree bölmesinde, bir **autoFillHints** uyarısına dikkat edin. Bu uyarı, bu uygulama için geçerli değildir ve bu döküman kapsamı dışındadır, dolayısıyla onu görmezden gelebilirsiniz.
3. Attributes bölmesinde, **EditText** görünümünün aşağıdaki nitelikleri için değerleri doğrulayın:


| Attribute | Value |
|---|---|
| id | nickname_edit |
| layout_width | match_parent (default) |
| layout_height | wrap_content (default) |
| style | @style/NameStyle |
| inputType | textPersonName |
| hint | "@string/what_is_your_nickname" |
| text | *(empty)* |

## <a name="3"></a>Bir Düğme Ekleyin ve Ona Stil Verin









