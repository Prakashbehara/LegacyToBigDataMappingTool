/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { SourceFeedMappingDetailComponent } from 'app/entities/source-feed-mapping/source-feed-mapping-detail.component';
import { SourceFeedMapping } from 'app/shared/model/source-feed-mapping.model';

describe('Component Tests', () => {
    describe('SourceFeedMapping Management Detail Component', () => {
        let comp: SourceFeedMappingDetailComponent;
        let fixture: ComponentFixture<SourceFeedMappingDetailComponent>;
        const route = ({ data: of({ sourceFeedMapping: new SourceFeedMapping(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [SourceFeedMappingDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SourceFeedMappingDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SourceFeedMappingDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.sourceFeedMapping).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
