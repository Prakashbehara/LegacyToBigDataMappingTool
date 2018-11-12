/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { SourceFeedDetailComponent } from 'app/entities/source-feed/source-feed-detail.component';
import { SourceFeed } from 'app/shared/model/source-feed.model';

describe('Component Tests', () => {
    describe('SourceFeed Management Detail Component', () => {
        let comp: SourceFeedDetailComponent;
        let fixture: ComponentFixture<SourceFeedDetailComponent>;
        const route = ({ data: of({ sourceFeed: new SourceFeed(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [SourceFeedDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SourceFeedDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SourceFeedDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.sourceFeed).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
