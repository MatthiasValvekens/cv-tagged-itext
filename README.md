# Tagged CV formatter

## Summary

This repository contains a small collection of tools to put together a CV in
PDF format that complies with both the requirements of ISO 14289-1:2014
(PDF/UA-1) and ISO 19005-2:2011 (PDF/A-2) at conformance level A.

The default formatting is inspired by the classic `moderncv` look and uses
Latin Modern fonts [as provided by GUST][GUST-LM]. The actual tagging
is performed by [iText 7](https://github.com/itext/itext7).

[GUST-LM]: http://www.gust.org.pl/projects/e-foundry/latin-modern "GUST LM download page"


## Relevant links

 * [`moderncv` on GitHub](https://github.com/moderncv/moderncv)
 * [iText](https://github.com/itext/itext7)
 * [VeraPDF](https://verapdf.org/home/#validation)


## Examples

 * Take a look in the `src/example` folder for some concrete example code.
 * The CV linked [on my website][hp] was produced using this process.

[hp]: https://mvalvekens.be/about.html "my about page"


## Ideas

 - A PDF 2.0 tagging mode &mdash; the only reason why I didn't go with PDF 2.0 tags from the start 
   is that there's no PDF/UA equivalent for PDF 2.0 yet.
 - Some sort of include/exclude mechanism to make it easier to generate CVs tailored towards specific
   audiences from the same data.


## Licencing

(c) 2022 Matthias Valvekens

Provided under the terms of the AGPL, see [LICENSE.md](LICENSE.md). Different conditions may apply for font resources.

