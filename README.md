<p align="center">
<img src="https://user-images.githubusercontent.com/2550945/29802052-07547f12-8c84-11e7-877d-49dabe5843a1.png" height="60px" width="60px"/>
</p>

<h3><p align="center">Youkai</p></h3>

<p align="center"><i>Cool new unofficial client app for <a href="https://kitsu.io">Kitsu.io</a></i></p>

![](https://user-images.githubusercontent.com/2550945/29801471-dfd43e12-8c80-11e7-899d-bd02b8a3ccf1.png)

<p align="right">
<a href='https://play.google.com/store/apps/details?id=app.youkai'><img height="48" alt='Get it on Google Play' src='https://cloud.githubusercontent.com/assets/2550945/21590908/dd7857a8-d0ff-11e6-9d0c-a8ce8ba883d4.png'/></a>
</p>
<p align="right">
<a href='https://github.com/xiprox/Youkai/releases/latest'><img height="48" alt='Get apk' src='https://cloud.githubusercontent.com/assets/2550945/21590907/dd74e0f0-d0ff-11e6-971f-d429148fd03d.png'/></a>
</p>

## Goals
Originally we planned to build this app as a full client for Kitsu. However, due to various reasons, we are shifting our goals. For the time being, all we can say is that it will be _something_, but it probably won't be a full client.

## Development & Contributions
### Technologies
Youkai interacts with Kitsu's [JSONAPI](http://docs.kitsu.apiary.io). Uses [jasminb/jsonapi-converter](https://github.com/jasminb/jsonapi-converter) to parse data, obtained through [Retrofit](https://square.github.io/retrofit). A bit of Rx is present. [Mosby](https://github.com/sockeqwe/mosby) is used for MVP. Fresco handles image download/display.

### Further info
- [Initial designs](https://github.com/xiprox/Youkai/wiki/Mockups)
- [Package Structure](https://github.com/xiprox/Youkai/wiki/Package-Structure)

### Contributing
We're a team of volunteers working in our free time. Things are slow and a helping hand wouldn't be turned back. If you are interested in throwing a grain of salt into this soup, feel free to reach out. We have a Slack chat that you can join too.

If you don't plan on sticking around too much but want to change that one thing, again, please do reach out before you commit your time into the work.

Oh, and if anything looks scary or you think you aren't qualified enough, don't worry, we are probably bigger noobs than you. We had no idea what JSONAPI was like when we began this project, and I (xip) have no idea how RxJava works.

## Alpha testing
The master branch is built and published on the alpha track on the Play Store automatically after every non-breaking push. If you'd like to have a taste, please join [this](https://plus.google.com/communities/113975934687482576411) Google+ Community, then register for alpha [here](https://play.google.com/apps/testing/app.youkai), and then install the app from the [listing page](https://play.google.com/store/apps/details?id=app.youkai).

## License
```
Copyright (C) 2017  The Youkai Team

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
```
GPL v3 ([LICENSE](/LICENSE))
